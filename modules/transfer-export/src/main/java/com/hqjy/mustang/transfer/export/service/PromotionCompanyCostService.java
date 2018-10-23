package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.CompanyCostReportData;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.util.PageUtil;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广公司费用报表数据服务层
 */
public interface PromotionCompanyCostService {
    /**
     * 获取推广公司费用报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    PageUtil<CompanyCostReportData> promotionCompanyCostList(PageParams params, CompanyCostQueryParams query);


    /**
     * 导出推广公司费用报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportPromotionCompanyCost(CompanyCostQueryParams query);
}
