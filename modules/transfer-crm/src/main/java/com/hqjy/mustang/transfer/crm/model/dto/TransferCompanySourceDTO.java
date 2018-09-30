package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;

@Data
public class TransferCompanySourceDTO {

    /**
     * 推广公司ID
     */
    private Long companyId;

    /**
     * 推广公司名称
     */
    private String companyName;

    /**
     * 平台来源Id
     */
    private Long sourceId;

    /**
     * 平台来源名称
     */
    private String sourceName;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态
     */
    private Byte status;
}
