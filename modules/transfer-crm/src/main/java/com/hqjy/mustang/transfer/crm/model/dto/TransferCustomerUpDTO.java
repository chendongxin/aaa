package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

@Data
public class TransferCustomerUpDTO {

    /**
     * 赛道ID
     */
    private Long proId;

    /**
     * 推广公司ID
     */
    private Long companyId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 人员ID
     */
    private Long userId;

    /**
     * 来源平台ID
     */
    private Long sourceId;

    /**
     * 获取方式 (1-主动获取，2-被动获取)
     */
    private Byte getWay;

    /**
     * 分配规则，是否不分配， 默认false分配，true 不自动分配
     */
    private Boolean notAllot;

}
