package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.CustomerReportData;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.CustomerQueryParams;
import com.hqjy.mustang.transfer.export.util.PageUtil;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 客服推广报表数据服务层
 */
public interface PromotionCustomerService {

    /**
     * 获取推广报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    PageUtil<CustomerReportData> promotionCustomerList(PageParams params, CustomerQueryParams query);


    /**
     * 导出推广报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportPromotionCustomer(CustomerQueryParams query);
}
