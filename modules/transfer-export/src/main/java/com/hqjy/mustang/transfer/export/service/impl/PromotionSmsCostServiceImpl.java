package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionSmsCostDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.*;
import com.hqjy.mustang.transfer.export.model.entity.SmsCostEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SmsCostQueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionSmsCostService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广短信费用报表数据服务层
 * 计算费用规则：发送失败不计费，每条短信固定0.035元，单条70字以内都是一条计费，最长可以发300字 超过70就是按67字每条计费
 */
@Service
public class PromotionSmsCostServiceImpl implements PromotionSmsCostService {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionSmsCostServiceImpl.class);

    private static final int CONTENT_LENGTH_BASE = 70;
    private static final double CONTENT_LENGTH_CARDINAL = 67.00;
    private static final double PRICE = 0.035;

    private PromotionSmsCostDao promotionSmsCostDao;
    private SysDeptServiceFeign deptServiceFeign;

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionSmsCostDao(PromotionSmsCostDao promotionSmsCostDao) {
        this.promotionSmsCostDao = promotionSmsCostDao;
    }

    @Override
    public SmsCostReportResult promotionSmsCostList(PageParams params, SmsCostQueryParams query) {
        List<SmsCostReportData> list = this.check(query);
        this.setData(query, list);
        SmsCostReportTotal total = this.countTotal(list);
        PageUtil<SmsCostReportData> pageList = new PageUtil<>(params, list);
        return new SmsCostReportResult().setPageList(pageList).setTotal(total);
    }

    private void setData(SmsCostQueryParams query, List<SmsCostReportData> list) {
        List<SmsCostEntity> entityList = promotionSmsCostDao.getData(query);
        List<SmsCostEntity> collect = entityList.stream().filter(x -> x.getStatus() == 2).collect(Collectors.toList());
        list.forEach(l -> {
            collect.forEach(x -> {
                if (l.getDeptId().equals(x.getDeptId())) {
                    if (x.getContent().length() <= CONTENT_LENGTH_BASE) {
                        l.setSendNum(l.getSendNum() + 1);
                    } else {
                        int count = (int) Math.ceil(x.getContent().length() / CONTENT_LENGTH_CARDINAL);
                        l.setSendNum(l.getSendNum() + count);
                    }
                    l.setSendSuccessNum(l.getSendSuccessNum() + 1);
                }
            });
            l.setCost(String.valueOf(new BigDecimal(l.getSendNum() * PRICE)));
        });
    }

    private List<SmsCostReportData> getDailyData(SmsCostQueryParams query) {
        List<SmsCostReportData> list = this.check(query);
        this.setData(query, list);
        return list;
    }

    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private SmsCostReportTotal countTotal(List<SmsCostReportData> list) {
        SmsCostReportTotal total = new SmsCostReportTotal();
        total.setDeptName("/");
        list.forEach(x -> {
            total.setSendNum(total.getSendNum() + x.getSendNum());
            total.setSendSuccessNum(total.getSendSuccessNum() + x.getSendSuccessNum());
            total.setCost(new BigDecimal(total.getCost()).add(new BigDecimal(x.getCost())).toString());
        });
        return total;
    }

    @Override
    public String exportPromotionSmsCost(SmsCostQueryParams query) {
        try {
            List<SmsCostReportData> list = this.getDailyData(query);
            SmsCostReportTotal total = this.countTotal(list);
            ExcelUtil<SmsCostReportData, SmsCostReportTotal> util1 = new ExcelUtil<>(SmsCostReportData.class, SmsCostReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, null, total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "招转推广短信费用报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("招转推广短信费用报表导出异常->{}", e.getMessage());
            throw new RRException("招转推广短信费用报表导出异常");
        }
    }

    private List<SmsCostReportData> check(SmsCostQueryParams query) {
        List<SmsCostReportData> list = new ArrayList<>();
        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId());
        if (deptInfo.isEmpty()) {
            throw new RRException("部门不存在");
        }
        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();
        if (deptList.isEmpty()) {
            throw new RRException("部门(校区)不存在");
        }
        AtomicInteger sequence = new AtomicInteger();
        deptList.forEach(y -> {
            list.add(new SmsCostReportData().setSequence(sequence.incrementAndGet()).setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }

}
