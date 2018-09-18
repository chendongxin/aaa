package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.QueryParams;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广报表数据服务层
 */
public interface PromotionService {

    /**
     * 获取招转推广报表数据列表
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    R promotionList(PageParams params, QueryParams query);


    /**
     * 导出招转推广报表数据
     *
     * @param query 高级请求参数
     * @return 返回查询结果
     */
    R exportPromotion(QueryParams query);
}
