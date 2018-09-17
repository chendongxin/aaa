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
public class QueryParams extends PromotionQueryObject {


    /**
     * 推广费用类型：人民币或虚拟币
     */
    private int costType;
}
