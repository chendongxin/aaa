package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionCustomerDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.CustomerReportData;
import com.hqjy.mustang.transfer.export.model.dto.CustomerReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.CustomerQueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionCustomerService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 客服推广报表数据服务层
 */
@Service
public class PromotionCustomerServiceImpl implements PromotionCustomerService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionDailyServiceImpl.class);

    private PromotionCustomerDao promotionCustomerDao;
    private SysDeptServiceFeign deptServiceFeign;

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionCustomerDao(PromotionCustomerDao promotionCustomerDao) {
        this.promotionCustomerDao = promotionCustomerDao;
    }

    @Override
    public PageUtil<CustomerReportData> promotionCustomerList(PageParams params, CustomerQueryParams query) {
        List<CustomerReportData> list = this.check(query);
        this.setSaleNum(query, list);
        return new PageUtil<>(params, list);
    }


    private void setSaleNum(CustomerQueryParams query, List<CustomerReportData> list) {
        List<CustomerEntity> createBusiness = promotionCustomerDao.countCreateBusiness(query);
        List<CustomerEntity> validBusiness = promotionCustomerDao.countValidBusiness(query);
        List<CustomerEntity> visitBusiness = promotionCustomerDao.countVisitBusiness(query);
        List<CustomerEntity> validVisitBusiness = promotionCustomerDao.countValidVisitBusiness(query);
        List<CustomerEntity> dealBusiness = promotionCustomerDao.countDealBusiness(query);
        list.forEach(x -> {
            //商机总量
            createBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setBusinessNum(y.getNum());
                }
            });
            //有效商机量
            validBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setValidNum(y.getNum());
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
            //成交量
            dealBusiness.forEach(y -> {
                if (x.getDeptId().equals(y.getDeptId())) {
                    x.setDealNum(y.getNum());
                }
            });
        });
    }

    private List<CustomerReportData> check(CustomerQueryParams query) {
        if (StringUtils.isEmpty(query.getBeginTime())) {
            throw new RRException("请选择开始时间");
        }
        if (StringUtils.isEmpty(query.getEndTime())) {
            throw new RRException("请选择结束时间");
        }
        if (query.getDeptId() == null) {
            throw new RRException("请选择部门");
        }
        List<CustomerReportData> list = new ArrayList<>();
        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId());

        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();

        deptList.forEach(y -> {
            LOG.info("初始化报表列表");
            list.add(new CustomerReportData().setDeptId(y.getDeptId()).setDeptName(y.getDeptName()));
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }


    /**
     * 合计报表数据
     *
     * @param list 报表数据集合
     * @return 返回合计对象
     */
    private CustomerReportTotal countTotal(List<CustomerReportData> list) {
        CustomerReportTotal total = new CustomerReportTotal();
        list.forEach(x -> {
            total.setBusinessNum(total.getBusinessNum() + x.getBusinessNum());
            total.setValidNum(total.getValidNum() + x.getValidNum());
            total.setVisitNum(total.getVisitNum() + x.getVisitNum());
            total.setVisitValidNum(total.getVisitValidNum() + x.getVisitValidNum());
            total.setDealNum(total.getDealNum() + x.getDealNum());
        });
        return total;
    }

    private List<CustomerReportData> getDailyData(CustomerQueryParams query) {
        List<CustomerReportData> list = this.check(query);
        this.setSaleNum(query, list);
        return list;
    }

    @Override
    public String exportPromotionCustomer(CustomerQueryParams query) {
        try {
            List<CustomerReportData> list = this.getDailyData(query);
            CustomerReportTotal total = this.countTotal(list);
            ExcelUtil<CustomerReportData, CustomerReportTotal> util1 = new ExcelUtil<>(CustomerReportData.class, CustomerReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            util1.getListToExcel(list, "招转客服推广报表_", total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "招转客服推广报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("招转客服推广报表导出异常->{}", e.getMessage());
            throw new RRException("招转客服推广报表导出异常");
        }
    }
}
