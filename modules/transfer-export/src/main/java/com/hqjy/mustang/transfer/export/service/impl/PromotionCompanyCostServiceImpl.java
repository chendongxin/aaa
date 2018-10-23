package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.export.dao.PromotionCompanyCostDao;
import com.hqjy.mustang.transfer.export.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.export.model.dto.CompanyCostReportData;
import com.hqjy.mustang.transfer.export.model.entity.CompanyCostEntity;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionCompanyCostService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广公司费用业务层
 */
@Service
public class PromotionCompanyCostServiceImpl implements PromotionCompanyCostService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionCompanyCostServiceImpl.class);

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

    @Override
    public PageUtil<CompanyCostReportData> promotionCompanyCostList(PageParams params, CompanyCostQueryParams query) {
        this.initParams(query);
        List<CompanyCostEntity> companyCost = promotionCompanyCostDao.getCompanyCost(query);
        List<CustomerEntity> customerEntities = promotionCompanyCostDao.countCustomer(query);

        Map<String, List<CompanyCostEntity>> collect = companyCost.stream().collect(Collectors.groupingBy(CompanyCostEntity::getDate));
        return null;
    }

    @Override
    public String exportPromotionCompanyCost(CompanyCostQueryParams query) {
        return null;
    }


    private void initParams(CompanyCostQueryParams query) {

        List<SysDeptInfo> deptInfo = deptServiceFeign.getDeptEntityByDeptId(query.getDeptId());
        List<SysDeptInfo> deptList = deptInfo.stream().filter(x -> x.getDeptName().contains("校区")).collect(Collectors.toList());
        List<String> ids = new ArrayList<>();
        deptList.forEach(y -> {
            ids.add(String.valueOf(y.getDeptId()));
        });
        query.setBeginTime(DateUtils.getBeginTime(query.getBeginTime()));
        query.setEndTime(DateUtils.getEndTime(query.getEndTime()));
        query.setDeptIds(StringUtils.listToString(ids));
    }
}
