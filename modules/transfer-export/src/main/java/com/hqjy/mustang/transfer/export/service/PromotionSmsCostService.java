package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.SmsCostReportData;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SmsCostQueryParams;
import com.hqjy.mustang.transfer.export.util.PageUtil;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广短信费用报表数据服务层
 */
public interface PromotionSmsCostService {

    /**
     * 获取推广短信费用报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    PageUtil<SmsCostReportData> promotionSmsCostList(PageParams params, SmsCostQueryParams query);


    /**
     * 导出推广短信费用报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportPromotionSmsCost(SmsCostQueryParams query);
}
