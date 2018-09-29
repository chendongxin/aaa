package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_reservation 预约客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
public class TransferCustomerReservationEntity implements Serializable {
    /**
	 * 主键 id
	 **/
    private Long id;

    /**
	 * 客户id customer_id
	 **/
    private Long customerId;

    /**
	 * 预约时间 appointment_time
	 **/
    private Date appointmentTime;

    /**
	 * 转NC状态（0-未转NC，1-已转NC） status
	 **/
    private Byte status;

    /**
	 *  上门状态(NC同步回传更新),见典VISIT_STATUS(0-未上门，1-已上门) visit_status
	 **/
    private Byte visitStatus;

    /**
	 * 预约老师编号 teacher_code
	 **/
    private String teacherCode;

    /**
	 * NC校区老师名称 teacher_name
	 **/
    private String teacherName;

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