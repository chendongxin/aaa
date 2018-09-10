package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer_invalid 无效客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
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
	 * 质检人id user_id
	 **/
    private Long userId;

    /**
	 * 质检员姓名 user_name
	 **/
    private String userName;

    /**
	 * 0：未质检（默认）   1：已质检 status
	 **/
    private Byte status;

    /**
	 * 商机状态 商机状态（0-有效商机，1-无效有效 biz_status
	 **/
    private Byte bizStatus;

    /**
	 * 无效说明 memo
	 **/
    private String memo;

    /**
	 * 见数据字典 见数据字典：无效类型（INVALID_TYPE） type
	 **/
    private Byte type;

    /**
	 * 确认说明 result
	 **/
    private String result;

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