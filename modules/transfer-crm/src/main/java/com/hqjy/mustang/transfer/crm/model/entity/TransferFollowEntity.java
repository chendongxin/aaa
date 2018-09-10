package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_follow 客户跟进记录实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferFollowEntity implements Serializable {
    /**
	 * 跟进编号 follow_id
	 **/
    private Long followId;

    /**
	 * 客户编号 customer_id
	 **/
    private Long customerId;

    /**
	 * 流程编号 process_id
	 **/
    private Long processId;

    /**
	 * 跟进状态 跟进状态：枚举值见数据字典表FOLLOW_STATUS follow_status
	 **/
    private Long followStatus;

    /**
	 * 沟通方式 沟通方式：枚举值见数据字典表COMMUNICATE_WAY contact_type
	 **/
    private Long contactType;

    /**
	 * 联系跟进记录 memo
	 **/
    private String memo;

    /**
	 * 下次联系时间 next_time
	 **/
    private Date nextTime;

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