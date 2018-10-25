package com.hqjy.mustang.common.model.crm;

import lombok.Data;


/**
 * @author xyq
 * @date create on 2018/10/24
 * @apiNote
 */
@Data
public class TransferGenWayInfo {

    /**
     * 编号 way_id
     **/
    private Long wayId;

    /**
     * 推广方式 gen_way
     **/
    private String genWay;

    /**
     * 状态,见数据字典STATUS status( 0-启用 1-禁用)
     **/
    private byte status;


}
