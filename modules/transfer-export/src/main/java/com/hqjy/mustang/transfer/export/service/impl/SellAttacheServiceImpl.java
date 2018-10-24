package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.SellAttacheDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportData;
import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellAttacheQueryParams;
import com.hqjy.mustang.transfer.export.service.SellAttacheService;
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
import java.util.stream.Collectors;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote 电销专员排行报表数据服务层
 */
@Service
public class SellAttacheServiceImpl implements SellAttacheService {

    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);
    @Autowired
    private SysDeptServiceFeign sysDeptServiceFeign;
    @Autowired
    private SellAttacheDao sellAttacheDao;

    /**
     * 获取推广报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    @Override
    public PageUtil<SellAttacheReportData> sellAttacheList(PageParams params, SellAttacheQueryParams query) {
        List<SellAttacheReportData> list = this.check(query);
        this.setSaleNum(query, list);
        this.setSaleRate(list);
        return new PageUtil<>(params, list);
    }

    private List<SellAttacheReportData> check(SellAttacheQueryParams query) {
        if (StringUtils.isEmpty(query.getBeginTime())) {
            throw new RRException("请选择开始时间");
        }
        if (StringUtils.isEmpty(query.getEndTime())) {
            throw new RRException("请选择结束时间");
        }
        if (query.getDeptId() == null) {
            throw new RRException("请选择部门");
        }
        List<SellAttacheReportData> list = new ArrayList<>();
        List<SysDeptInfo> deptInfo = sysDeptServiceFeign.getDeptEntityByDeptId(query.getDeptId());

        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();

        deptList.forEach(y -> {
            LOG.info("初始化报表列表");
            list.add(new SellAttacheReportData().setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }

    private void setSaleNum(SellAttacheQueryParams query, List<SellAttacheReportData> list) {
        List<CustomerEntity> visitBusiness = sellAttacheDao.countVisitBusiness(query);
        List<CustomerEntity> validBusiness = sellAttacheDao.countValidBusiness(query);
        List<CustomerEntity> dealBusiness = sellAttacheDao.countDealBusiness(query);
        List<CustomerEntity> allotBusiness = sellAttacheDao.countAllotBusiness(query);
        List<CustomerEntity> visitTodayAppointBusiness = sellAttacheDao.countVisitTodayAppointBusiness(query);
        List<CustomerEntity> visitTomoAppointBusiness = sellAttacheDao.countVisitTomoAppointBusiness(query);
        List<CustomerEntity> visitValidBusiness = sellAttacheDao.countVisitValidBusiness(query);
        list.forEach(x -> {
            //上门量
            visitBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitNum(y.getNum());
                }
            });
            //商机有效量
            validBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setValidNum(y.getNum());
                }
            });
            //成交量
            dealBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setDealNum(y.getNum());
                }
            });
            //分配商机量
            allotBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setAllotNum(y.getNum());
                }
            });
            //今日预约上门量
            visitTodayAppointBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitTodayAppointNum(y.getNum());
                }
            });
            //明日预约上门量
            visitTomoAppointBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitTomorrowAppointNum(y.getNum());
                }
            });
            //有效上门量
            visitValidBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setVisitValidNum(y.getNum());
                }
            });
        });
    }

    private void setSaleRate(List<SellAttacheReportData> list) {
        DecimalFormat df = new DecimalFormat("0.00%");
        list.forEach(x -> {
            //有效商机上门率:有效上门量/有效商机总量
            x.setVisitValidRate(df.format(x.getValidNum() == 0 ? 0 : (double) x.getVisitValidNum() / x.getValidNum()));
            //商机有效率:有效商机量/分配给电销专员商机量
            x.setValidRate(df.format(x.getAllotNum() == 0 ? 0 : (double) x.getValidNum() / x.getAllotNum()));
            //实际上门率:有效上门量/电销专员商机量
            x.setVisitValidRate(df.format(x.getAllotNum() == 0 ? 0 : (double) x.getVisitValidNum() / x.getAllotNum()));
        });
    }

    @Override
    public String exportSellAttache(SellAttacheQueryParams query) {
        try {
            List<SellAttacheReportData> list = this.getSellAttacheData(query);
            SellAttacheReportTotal total = this.countTotal(list);
            ExcelUtil<SellAttacheReportData, SellAttacheReportTotal> util1 = new ExcelUtil<>(SellAttacheReportData.class, SellAttacheReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, null, total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "电销专员排行数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("电销专员排行数据报表导出异常->{}", e.getMessage());
            throw new RRException("电销专员排行数据报表导出异常");
        }
    }

    private List<SellAttacheReportData> getSellAttacheData(SellAttacheQueryParams query) {
        List<SellAttacheReportData> list = this.check(query);
        this.setSaleNum(query, list);
        this.setSaleRate(list);
        return list;
    }

    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private SellAttacheReportTotal countTotal(List<SellAttacheReportData> list) {
        SellAttacheReportTotal total = new SellAttacheReportTotal();
        list.forEach(x -> {
            total.setVisitNum(total.getVisitNum() + x.getVisitNum());
            total.setValidNum(total.getValidNum() + x.getValidNum());
            total.setDealNum(total.getDealNum() + x.getDealNum());
            total.setAllotNum(total.getAllotNum() + x.getAllotNum());
            total.setVisitTodayAppointNum(total.getVisitTodayAppointNum() + x.getVisitTodayAppointNum());
            total.setVisitTomorrowAppointNum(total.getVisitTomorrowAppointNum() + x.getVisitTomorrowAppointNum());
            total.setVisitValidNum(total.getVisitValidNum() + x.getVisitValidNum());
        });
        DecimalFormat df = new DecimalFormat("0.00%");
        //有效商机上门率:有效上门量/有效商机总量
        total.setVisitValidRate(df.format(total.getValidNum() == 0 ? 0 : (double) total.getVisitValidNum() / total.getValidNum()));
        //商机有效率:有效商机量/分配给电销专员商机量
        total.setValidRate(df.format(total.getAllotNum() == 0 ? 0 : (double) total.getValidNum() / total.getAllotNum()));
        //实际上门率:有效上门量/电销专员商机量
        total.setVisitValidRate(df.format(total.getAllotNum() == 0 ? 0 : (double) total.getVisitValidNum() / total.getAllotNum()));
        return total;
    }
}
