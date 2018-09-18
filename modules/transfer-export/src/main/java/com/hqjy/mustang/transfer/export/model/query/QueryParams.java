package com.hqjy.mustang.transfer.export.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广报表查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryParams extends QueryObject {

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
    /**
     * 推广费用类型：人民币或虚拟币
     */
    private int costType;
}
