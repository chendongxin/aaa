package com.hqjy.mustang.transfer.export.model.query;

import lombok.Data;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 分页查询
 */
@Data
public class PageParams {

    /**
     * 当前页数
     */
    private int pageNum=1;

    /**
     * 每页大小
     */
    private int pageSize=10;
}
