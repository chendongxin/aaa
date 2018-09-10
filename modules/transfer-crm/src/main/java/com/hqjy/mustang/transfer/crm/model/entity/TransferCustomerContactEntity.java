package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer_contact 客户联系方式实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferCustomerContactEntity implements Serializable {
    /**
	 * 主键 contact_id
	 **/
    private Integer contactId;

    /**
	 * 客户编号 customer_id
	 **/
    private Integer customerId;

    /**
	 * 类型 type
	 **/
    private Integer type;

    /**
	 * 联系方式详情 detail
	 **/
    private String detail;

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