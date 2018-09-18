package com.hqjy.mustang.transfer.export.model.query;

import lombok.Data;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广报表父类查询参数
 */
@Data
public class QueryObject {

    /**
     * 当前页数
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;
}
