package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.ExcelUtil;
import com.hqjy.mustang.common.base.utils.OssFileUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionCompanyCostDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.CompanyCostReportData;
import com.hqjy.mustang.transfer.export.model.dto.CompanyCostReportTotal;
import com.hqjy.mustang.transfer.export.model.entity.CompanyCostEntity;
import com.hqjy.mustang.transfer.export.model.entity.CompanyCustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionCompanyCostService;
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

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广公司费用业务层
 */
@Service
public class PromotionCompanyCostServiceImpl implements PromotionCompanyCostService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionCompanyCostServiceImpl.class);
    private final static String BOUTIQUE = "精品(帮帮)";
    private final static String PRECISION = "精准(黄金展位)";
    private final static String FLUSH = "刷新";
    private final static String JOB_POSTING = "职位发布";
    private final static String TOPPING = "置顶";
    private final static String LOAD = "下载";
    private final static String AD = "广告";
    private final static String PUSH = "优先推送";
    private final static String INITIATIVE_RMB = "人民币主动";
    private final static String INITIATIVE_VIRTUAL = "虚拟币主动";
    private final static String PASSIVE_RMB = "人民币被动";
    private final static String PASSIVE_VIRTUAL = "虚拟币被动";

    private PromotionCompanyCostDao promotionCompanyCostDao;
    private SysDeptServiceFeign deptServiceFeign;

    @Autowired
    public void setDeptServiceFeign(SysDeptServiceFeign deptServiceFeign) {
        this.deptServiceFeign = deptServiceFeign;
    }

    @Autowired
    public void setPromotionCompanyCostDao(PromotionCompanyCostDao promotionCompanyCostDao) {
        this.promotionCompanyCostDao = promotionCompanyCostDao;
    }

    /**
     * 获取推广公司费用报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    @Override
    public PageUtil<CompanyCostReportData> promotionCompanyCostList(PageParams params, CompanyCostQueryParams query) {
        List<CompanyCostReportData> list = this.initData(query);
        this.setCompanyCostData(query, list);
        return new PageUtil<>(params, list);
    }

    /**
     * 赋值公司费用报表具体的数据
     *
     * @param query 前端请求参数
     * @param list  初始化后的推广公司费用报表对象
     */
    private void setCompanyCostData(CompanyCostQueryParams query, List<CompanyCostReportData> list) {
        List<String> costType = this.checkCostType(query);
        List<CompanyCostEntity> companyCost = promotionCompanyCostDao.getCompanyCost(query);
        List<CompanyCustomerEntity> customerEntities = promotionCompanyCostDao.countCustomer(query);

        list.forEach(x -> {
            customerEntities.forEach(y -> {
                if (x.getDate().equals(y.getDate())) {
                    x.setNum(y.getNum());
                }
            });
            companyCost.forEach(y -> {
                BigDecimal money = this.getCostTypeMoney(costType, y);
                if (x.getDate().equals(y.getDate())) {
                    this.setCost(x, y, money);
                }
            });
            x.setTotalCost(x.getBoutiqueCost()
                    .add(x.getPrecisionCost()
                            .add(x.getJobPostingCost()
                                    .add(x.getAdCost()
                                            .add(x.getFlushCost()
                                                    .add(x.getLoadCost()
                                                            .add(x.getToppingCost()
                                                                    .add(x.getPushCost()))))))));
        });
    }

    /**
     * 判断费用类型
     *
     * @param query 前端请求参数
     * @return 返回报表计算费用类型
     */
    private List<String> checkCostType(CompanyCostQueryParams query) {
        List<String> costType = new ArrayList<>();
        if (query.getGetWay() == Constant.GetWayStatus.ACTIVE_GET.getValue()) {
            if (query.getCostType() == Constant.CostType.RMB.getValue()) {
                costType.add(INITIATIVE_RMB);
            } else {
                costType.add(INITIATIVE_VIRTUAL);
            }
        } else {
            if (query.getCostType() == Constant.CostType.RMB.getValue()) {
                costType.add(PASSIVE_RMB);
            } else {
                costType.add(PASSIVE_VIRTUAL);
            }
        }
        return costType;
    }

    /**
     * 设置各推广方式的费用
     *
     * @param x     报表数据对象
     * @param y     推广公司费用对象
     * @param money 费用
     */
    private void setCost(CompanyCostReportData x, CompanyCostEntity y, BigDecimal money) {
        switch (y.getWayName()) {
            case BOUTIQUE:
                x.setBoutiqueCost(money);
                break;
            case PRECISION:
                x.setPrecisionCost(money);
                break;
            case FLUSH:
                x.setFlushCost(money);
                break;
            case JOB_POSTING:
                x.setJobPostingCost(money);
                break;
            case TOPPING:
                x.setToppingCost(money);
                break;
            case LOAD:
                x.setLoadCost(money);
                break;
            case AD:
                x.setBoutiqueCost(money);
                break;
            case PUSH:
                x.setPushCost(money);
                break;
            default:
        }
    }

    /**
     * 获取所选费用类型对应的费用
     *
     * @param costType 费用类型
     * @param y        推广公司费用对象
     * @return 返回费用
     */
    private BigDecimal getCostTypeMoney(List<String> costType, CompanyCostEntity y) {
        BigDecimal money;
        switch (costType.get(0)) {
            case INITIATIVE_RMB:
                money = y.getInitiativeMoney();
                break;
            case INITIATIVE_VIRTUAL:
                money = y.getInitiativeVirtual();
                break;
            case PASSIVE_RMB:
                money = y.getPassiveMoney();
                break;
            case PASSIVE_VIRTUAL:
                money = y.getPassiveVirtual();
                break;
            default:
                money = BigDecimal.ZERO;
        }
        return money;
    }


    /**
     * 导出推广公司费用报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    @Override
    public String exportPromotionCompanyCost(CompanyCostQueryParams query) {
        try {
            List<CompanyCostReportData> list = this.getCompanyCostData(query);
            CompanyCostReportTotal total = this.countTotal(list);
            ExcelUtil<CompanyCostReportData, CompanyCostReportTotal> util1 = new ExcelUtil<>(CompanyCostReportData.class, CompanyCostReportTotal.class);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            String sheetHead = query.getDeptName() + "_" + query.getCompanyName() + "_" + query.getSourceName();
            util1.getListToExcel(list, sheetHead, total, os);
            //aliyun目录
            String dir = "export";
            //文件名称
            String fileName = "招转推广公司费用报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".xls";
            //上传文件至阿里云
            String recordFile = OssFileUtils.uploadFile(os.toByteArray(), dir, fileName);
            //下载地址有效时间1个小时
            URL visitUrl = OssFileUtils.getVisitUrl(recordFile, 3600);
            return visitUrl.toString();
        } catch (Exception e) {
            LOG.error("招转推广公司费用导出异常->{}", e.getMessage());
            throw new RRException("招转推广公司费用导出异常");
        }
    }

    private CompanyCostReportTotal countTotal(List<CompanyCostReportData> list) {
        CompanyCostReportTotal total = new CompanyCostReportTotal();
        list.forEach(x -> {
            total.setNum(x.getNum() + total.getNum());
            total.setTotalCost(x.getTotalCost().add(total.getTotalCost()));
            total.setBoutiqueCost(x.getBoutiqueCost().add(total.getBoutiqueCost()));
            total.setPrecisionCost(x.getPrecisionCost().add(total.getPrecisionCost()));
            total.setJobPostingCost(x.getJobPostingCost().add(total.getJobPostingCost()));
            total.setAdCost(x.getAdCost().add(total.getAdCost()));
            total.setFlushCost(x.getFlushCost().add(total.getFlushCost()));
            total.setLoadCost(x.getLoadCost().add(total.getLoadCost()));
            total.setToppingCost(x.getToppingCost().add(total.getToppingCost()));
        });
        return total;
    }

    /**
     * 获取推广公司费用报表数据集合
     *
     * @param query 前端请求参数
     * @return 报表数据
     */
    private List<CompanyCostReportData> getCompanyCostData(CompanyCostQueryParams query) {
        List<CompanyCostReportData> list = this.initData(query);
        this.setCompanyCostData(query, list);
        return list;
    }


    /**
     * 初始化 推广公司费用报表对象
     *
     * @param query 前端请求查询条件
     * @return 返回初始化对象
     */
    private List<CompanyCostReportData> initData(CompanyCostQueryParams query) {

        List<CompanyCostReportData> list = new ArrayList<>();
        List<String> dates = DateUtils.getBetweenDates(query.getBeginTime(), query.getEndTime());
        dates.forEach(x -> {
            list.add(new CompanyCostReportData().setDate(x));
        });
        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId().longValue());
        List<String> ids = new ArrayList<>();
        if (deptInfo.isEmpty()) {
            throw new RRException("部门不存在");
        }
        deptInfo.forEach(y -> {
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
        return list;
    }
}
