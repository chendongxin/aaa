package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

@Data
public class TransferGenWaySourceDTO {

    /**
     * 编号
     */
    private Long id;

    /**
     * 推广方式id
     */
    private Long wayId;

    /**
     * 推广方式名称
     */
    private String genWay;

    /**
     * 推广平台id
     */
    private Long sourceId;

    /**
     * 推广平台名称
     */
    private String sourceName;

    /**
     * 排序号
     */
    private Integer seq;

    /**
     * 状态
     */
    private byte status;

    /**
     * 被用标志 0，未被用，1被用
     */
    private Integer sign;
}
