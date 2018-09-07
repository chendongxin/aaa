package com.hqjy.mustang.transfer.crm.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer_reservation 预约客户实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
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
	 * 到访状态 到访状态（见数字字典VISIT_STATUS）:默认0-未到访，1-已到访，-1到访流失 visit_status
	 **/
    private Byte visitStatus;

    /**
	 * 流失原因 lost_reason
	 **/
    private String lostReason;

    /**
	 * NC省份编码 province_code
	 **/
    private String provinceCode;

    /**
	 * NC省份名称 province_name
	 **/
    private String provinceName;

    /**
	 * 预约老师编号 teacher_code
	 **/
    private String teacherCode;

    /**
	 * 校区老师名称 teacher_name
	 **/
    private String teacherName;

    /**
	 * 预约校区NCId school_nc_id
	 **/
    private String schoolNcId;

    /**
	 * 校区名称 school_name
	 **/
    private String schoolName;

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