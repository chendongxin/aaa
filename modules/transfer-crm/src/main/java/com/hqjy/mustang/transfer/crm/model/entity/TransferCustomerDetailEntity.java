package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * transfer_customer_detail 客户详情信息实体类
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
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
	 * 性别:见数据字典SEX枚举 sex
	 **/
    private Byte sex;

    /**
	 * 地址 address
	 **/
    private String address;

    /**
	 * 备注 note
	 **/
    private String note;

    /**
	 * 邮箱 email
	 **/
    private String email;

    /**
	 * 生日 birthday
	 **/
    private Date birthday;

    /**
	 * 毕业学校 school
	 **/
    private String school;

    /**
	 * 毕业时间 graduate_time
	 **/
    private Date graduateTime;

    /**
	 * 专业 major
	 **/
    private String major;

    /**
	 * 学历 education_id
	 **/
    private Long educationId;

    /**
	 * 应聘职位 position_applied
	 **/
    private String positionApplied;

    /**
	 * 期望工作地点 working_place
	 **/
    private String workingPlace;

    /**
	 * 期望薪酬 salary
	 **/
    private String salary;

    /**
	 * 工作经历 work_experience
	 **/
    private String workExperience;

    /**
	 * 教育经历 education_experience
	 **/
    private String educationExperience;

    /**
	 * 项目经验 project_experience
	 **/
    private String projectExperience;

    /**
	 * 社会实践 social_practice
	 **/
    private String socialPractice;

    /**
	 * 培训经历 trained_experience
	 **/
    private String trainedExperience;

    /**
	 * 语言能力 language_ability
	 **/
    private String languageAbility;

    /**
	 * IT技能 it_skills
	 **/
    private String itSkills;

    /**
	 * 证书 certificate
	 **/
    private String certificate;

    /**
	 * 自我描述 self_description
	 **/
    private String selfDescription;

    /**
	 * 获得奖励 rewards
	 **/
    private String rewards;

    /**
	 * 其他信息 other
	 **/
    private String other;

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