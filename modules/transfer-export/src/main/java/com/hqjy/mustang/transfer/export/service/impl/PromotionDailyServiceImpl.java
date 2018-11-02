package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionDailyDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportData;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportResult;
import com.hqjy.mustang.transfer.export.model.dto.DailyReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionDailyService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转日常报表数据服务层
 */
@Service
public class PromotionDailyServiceImpl implements PromotionDailyService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);

    private PromotionDailyDao promotionDailyDao;
    private SysDeptServiceFeign deptServiceFeign;

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionDailyDao(PromotionDailyDao promotionDailyDao) {
        this.promotionDailyDao = promotionDailyDao;
    }

    @Override
    public DailyReportResult promotionDailyList(PageParams params, DailyQueryParams query) {

        List<DailyReportData> list = this.check(query);
        if (!list.isEmpty()) {
            this.setSaleNum(query, list);
            this.setSaleRate(list);
        }
        //合计（不分页）
        DailyReportTotal total = this.countTotal(list);
        //分页
        PageUtil<DailyReportData> pageList = new PageUtil<>(params, list);
        return new DailyReportResult().setPageList(pageList).setTotal(total);
    }

    private void setSaleRate(List<DailyReportData> list) {
        DecimalFormat df = new DecimalFormat("0.00%");
        list.forEach(x -> {
            //有效商机率:有效商机量/商机总量
            x.setValidRate(df.format(x.getBusinessNum() == 0 ? 0 : (double) x.getValidNum() / x.getBusinessNum()));
            //非失败率:非失败商机量/商机总量
            x.setUnFailRate(df.format(x.getBusinessNum() == 0 ? 0 : (double) x.getUnFailNum() / x.getBusinessNum()));
            //有效上门率:有效上门量/上门量
            x.setVisitValidRate(df.format(x.getVisitNum() == 0 ? 0 : (double) x.getVisitValidNum() / x.getVisitNum()));
            //上门意向率:意向量/上门量
            x.setVisitIntentionRate(df.format(x.getVisitNum() == 0 ? 0 : (double) x.getIntentionNum() / x.getVisitNum()));
            //上门成交率:成交量/有效上门量
            x.setVisitDealRate(df.format(x.getVisitValidNum() == 0 ? 0 : (double) x.getDealNum() / x.getVisitValidNum()));
        });
    }

    private void setSaleNum(DailyQueryParams query, List<DailyReportData> list) {
        List<CustomerEntity> createBusiness = promotionDailyDao.countCreateBusiness(query);
        List<CustomerEntity> followBusiness = promotionDailyDao.countFollowBusiness(query);
        List<CustomerEntity> validBusiness = promotionDailyDao.countValidBusiness(query);
        List<CustomerEntity> noInvalidBusiness = promotionDailyDao.countNoInvalidBusiness(query);
        List<CustomerEntity> visitBusiness = promotionDailyDao.countVisitBusiness(query);
        List<CustomerEntity> validVisitBusiness = promotionDailyDao.countValidVisitBusiness(query);
        List<CustomerEntity> intentionBusiness = promotionDailyDao.countIntentionBusiness(query);
        List<CustomerEntity> dealBusiness = promotionDailyDao.countDealBusiness(query);
        list.forEach(x -> {
            //商机总量
            createBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setBusinessNum(y.getNum());
                }
            });
            //已跟进商机量
            followBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setFollowNum(y.getNum());
                }
            });
            //有效商机量
            validBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setValidNum(y.getNum());
                }
            });
            //非失败商机量
            noInvalidBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setUnFailNum(y.getNum());
                }
            });
            //商机上门量
            visitBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitNum(y.getNum());
                }
            });
            //有效上门量
            validVisitBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitValidNum(y.getNum());
                }
            });
            //意向量
            intentionBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setIntentionNum(y.getNum());
                }
            });
            //成交量
            dealBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setDealNum(y.getNum());
                }
            });
        });
    }

    private List<DailyReportData> check(DailyQueryParams query) {

        List<DailyReportData> list = new ArrayList<>();
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
            LOG.info("初始化报表列表");
            list.add(new DailyReportData().setSequence(sequence.incrementAndGet()).setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }


    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private DailyReportTotal countTotal(List<DailyReportData> list) {
        DailyReportTotal total = new DailyReportTotal();
        list.forEach(x -> {
            total.setBusinessNum(total.getBusinessNum() + x.getBusinessNum());
            total.setFollowNum(total.getFollowNum() + x.getFollowNum());
            total.setValidNum(total.getValidNum() + x.getValidNum());
            total.setUnFailNum(total.getUnFailNum() + x.getUnFailNum());
            total.setVisitNum(total.getVisitNum() + x.getVisitNum());
            total.setVisitValidNum(total.getVisitValidNum() + x.getVisitValidNum());
            total.setIntentionNum(total.getIntentionNum() + x.getIntentionNum());
            total.setDealNum(total.getDealNum() + x.getDealNum());
        });
        DecimalFormat df = new DecimalFormat("0.00%");
        //有效商机率:有效商机量/商机总量
        total.setValidRate(df.format(total.getBusinessNum() == 0 ? 0 : (double) total.getValidNum() / total.getBusinessNum()));
        //非失败率:非失败商机量/商机总量
        total.setUnFailRate(df.format(total.getBusinessNum() == 0 ? 0 : (double) total.getUnFailNum() / total.getBusinessNum()));
        //有效上门率:有效上门量/上门量
        total.setVisitValidRate(df.format(total.getVisitNum() == 0 ? 0 : (double) total.getVisitValidNum() / total.getVisitNum()));
        //上门意向率:意向量/上门量
        total.setVisitIntentionRate(df.format(total.getVisitNum() == 0 ? 0 : (double) total.getIntentionNum() / total.getVisitNum()));
        //上门成交率:成交量/有效上门量
        total.setVisitDealRate(df.format(total.getVisitValidNum() == 0 ? 0 : (double) total.getDealNum() / total.getVisitValidNum()));
        return total;
    }

    private List<DailyReportData> getDailyData(DailyQueryParams query) {
        List<DailyReportData> list = this.check(query);
        this.setSaleNum(query, list);
        this.setSaleRate(list);
        return list;
    }

    @Override
    public String exportPromotionDaily(DailyQueryParams query) {
        try {
            List<DailyReportData> list = this.getDailyData(query);
            DailyReportTotal total = this.countTotal(list);
            ExcelUtil<DailyReportData, DailyReportTotal> util1 = new ExcelUtil<>(DailyReportData.class, DailyReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, null, total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "招转日常数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("招转日常数据报表导出异常->{}", e.getMessage());
            throw new RRException("招转日常数据报表导出异常");
        }
    }
}
