package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_invalid 失败客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
public class TransferCustomerInvalidEntity implements Serializable {
    /**
	 * 自增主键 invalid_id
	 **/
    private Long invalidId;

    /**
	 * 客户id customer_id
	 **/
    private Long customerId;

    /**
	 * 失败状态（1-(失败)有效，2-（失败）无效 status
	 **/
    private Byte status;

    /**
	 * 见数据字典：失败(有效)类型（VALID_TYPE）和失败(无效)类型（INVALID） type
	 **/
    private Byte type;

    /**
	 * 失败操作说明 memo
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