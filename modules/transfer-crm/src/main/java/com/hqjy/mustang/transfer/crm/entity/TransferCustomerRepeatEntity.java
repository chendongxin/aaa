package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer_repeat 重单客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferCustomerRepeatEntity implements Serializable {
    /**
	 * 重单主键 repeat_id
	 **/
    private Long repeatId;

    /**
	 * 客户主键 customer_id
	 **/
    private Long customerId;

    /**
	 * 部门ID dept_id
	 **/
    private Long deptId;

    /**
	 * 部门名称 dept_name
	 **/
    private String deptName;

    /**
	 * 用户Id user_id
	 **/
    private Long userId;

    /**
	 * 用户名称 user_name
	 **/
    private String userName;

    /**
	 * 客户名称 name
	 **/
    private String name;

    /**
	 * 地址 address
	 **/
    private String address;

    /**
	 * 来源url url
	 **/
    private String url;

    /**
	 * 客户来源 source_id
	 **/
    private Long sourceId;

    /**
	 * 备注 memo
	 **/
    private String memo;

    /**
	 * 创建人编号 create_user_id
	 **/
    private Long createUserId;

    /**
	 * 创建人名称 create_user_name
	 **/
    private String createUserName;

    /**
	 * 创建时间 create_time
	 **/
    private Date createTime;

    /**
	 * 更新人编号 update_user_id
	 **/
    private Long updateUserId;

    /**
	 * 更新人名称 update_user_name
	 **/
    private String updateUserName;

    /**
	 * 更新时间 update_time
	 **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}