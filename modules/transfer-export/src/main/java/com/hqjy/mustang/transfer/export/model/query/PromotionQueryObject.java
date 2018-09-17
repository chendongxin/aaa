package com.hqjy.mustang.transfer.export.model.query;

import lombok.Data;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广报表父类查询参数
 */
@Data
class PromotionQueryObject {

    /**
     * 日期（必选）
     */
    private String date;
    /**
     * 赛道
     */
    private Long productId;
    /**
     * 推广公司
     */
    private Long companyId;
    /**
     * 来源平台
     */
    private Long sourceId;
    /**
     * 获取方式
     */
    private Long getWay;
}
