package com.hqjy.mustang.transfer.crm.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer_detail 客户明细信息实体类
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
@Accessors(chain = true)
public class TransferCustomerDetailEntity implements Serializable {
    /**
	 * 编号 id
	 **/
    private Long id;

    /**
	 * 客户编号 customer_id
	 **/
    private Long customerId;

    /**
	 * 年龄 age
	 **/
    private Byte age;

    /**
	 * 性别:见数据字典SEX枚举(0-未知，1-男，2-女) sex
	 **/
    private Byte sex;

    /**
	 * 地址 address
	 **/
    private String address;

    /**
	 * 邮箱 email
	 **/
    private String email;

    /**
	 * 毕业学校 school
	 **/
    private String school;

    /**
	 * 毕业年份 graduate_date
	 **/
    private Date graduateDate;

    /**
	 * 专业 major
	 **/
    private String major;

    /**
	 * 学历ID(数据来源于transfer_education学历表) education_id
	 **/
    private Long educationId;

    /**
	 * 学历名称(数据来源于transfer_education学历表) education_name
	 **/
    private String educationName;

    /**
	 * 应聘类别 apply_type
	 **/
    private String applyType;

    /**
	 * 应聘关键词 apply_key
	 **/
    private String applyKey;

    /**
	 * 期望职位 position_applied
	 **/
    private String positionApplied;

    /**
	 * 期望工作地点 working_place
	 **/
    private String workingPlace;

    /**
	 * 工作经验 work_experience
	 **/
    private Byte workExperience;

    /**
	 * 备注 note
	 **/
    private String note;

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

    /**
	 * 简历详情 resume_detail
	 **/
    private String resumeDetail;

    private static final long serialVersionUID = 1L;
}