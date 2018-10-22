package com.hqjy.mustang.transfer.export.service.impl;

import com.hqjy.mustang.transfer.export.model.dto.CompanyCostReportData;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.service.PromotionCompanyCostService;
import com.hqjy.mustang.transfer.export.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广公司费用业务层
 */
@Service
public class PromotionCompanyCostServiceImpl implements PromotionCompanyCostService {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionCompanyCostServiceImpl.class);


    @Override
    public PageUtil<CompanyCostReportData> promotionCompanyCostList(PageParams params, CompanyCostQueryParams query) {
        return null;
    }

    @Override
    public String exportPromotionCompanyCost(CompanyCostQueryParams query) {
        return null;
    }
}
