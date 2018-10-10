package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import java.util.List;

/**
 * @author gmm
 * @ description
 * @ date create in 2018/9/20 15:28
 */

@Data
public class TransferCustomerTransferDTO {

    /**
     * 客户编号
     **/
    private List<Long> customerId;

    /**
     * 部门Id
     **/
    private Long deptId;

    /**
     * 部门name
     **/
    private String deptName;

    /**
     * 业务员Id
     **/
    private Long userId;

    /**
     * 业务员名称
     */
    private String userName;
}
