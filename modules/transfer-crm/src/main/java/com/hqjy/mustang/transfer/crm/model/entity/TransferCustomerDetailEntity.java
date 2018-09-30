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
    private Integer sex;

    /**
	 * 地址 address
	 **/
    private String address;

    /**
	 * 邮箱 email
	 **/
    private String email;

    /**
     * 手机
     */
    private String phone;

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
	 * 学历ID education_id (0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士，7-博士)
	 **/
    private Long educationId;

    /**
	 * 学历名称
	 **/
    private String educationName;

    /**
	 * 应聘类别 apply_type (1-开发类，2-设计类，3-运营类，4-产品类，5-技术支持类，6-其它)
	 **/
    private Long applyType;

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
	 * 工作经验 work_experience (0-无经验，1-应届生，2-一年以内，3-两年，4-三年，5-三年以上)
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