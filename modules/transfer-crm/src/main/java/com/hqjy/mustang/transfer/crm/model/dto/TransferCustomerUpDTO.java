package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

@Data
public class TransferCustomerUpDTO {

    /**
     * 赛道ID
     */
    private Long proId;

    /**
     * 赛道名称
     */
    private String proName;

    /**
     * 推广公司ID
     */
    private Long companyId;

    /**
     * 推广公司名称
     */
    private String companyName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 人员ID
     */
    private Long userId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 来源平台ID
     */
    private Long sourceId;

    /**
     * 来源平台名称
     */
    private String sourceName;

    /**
     * 获取方式 (1-主动获取，2-被动获取)
     */
    private Byte getWay;

    /**
     * 分配规则，是否不分配， 默认false分配，true 不自动分配
     */
    private Boolean notAllot;

}
