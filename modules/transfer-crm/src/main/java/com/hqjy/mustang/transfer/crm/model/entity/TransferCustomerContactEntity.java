package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_contact 客户联系方式实体类
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
@Accessors(chain = true)
public class TransferCustomerContactEntity implements Serializable {
    /**
	 * 主键 contact_id
	 **/
    private Long contactId;

    /**
	 * 客户编号 customer_id
	 **/
    private Long customerId;

    /**
     * 赛道编号 pro_id
     */
    private Long proId;

    /**
	 * 类型;数据字典CONTACT_TYPE(1-手机，2-座机，3-微信，4-QQ) type
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