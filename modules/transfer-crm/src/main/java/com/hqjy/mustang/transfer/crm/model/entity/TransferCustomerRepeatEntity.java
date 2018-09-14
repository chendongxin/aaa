package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_repeat 实体类
 * 
 * @author : xyq
 * @date : 2018/09/14 15:58
 */
@Data
public class TransferCustomerRepeatEntity implements Serializable {
    /**
	 * 重单主键 repeat_id
	 **/
    private Long repeatId;

    /**
	 * 客户ID customer_id
	 **/
    private Long customerId;

    /**
	 * 产品ID pro_id
	 **/
    private Long proId;

    /**
	 * 产品（赛道）名称 pro_name
	 **/
    private String proName;

    /**
	 * 推广公司ID company_id
	 **/
    private Long companyId;

    /**
	 * 客户来源ID source_id
	 **/
    private Long sourceId;

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
	 * 手机号码 phone
	 **/
    private String phone;

    /**
	 * 微信 we_chat
	 **/
    private String weChat;

    /**
	 * qq qq
	 **/
    private String qq;

    /**
	 * 座机 land_line
	 **/
    private String landLine;

    /**
	 * 备注 memo
	 **/
    private String memo;

    /**
	 * 创建人ID create_user_id
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
	 * 更新人ID update_user_id
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