package com.hqjy.mustang.transfer.crawler.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_resume 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/20 17:33
 */
@Data
public class TransferResumeEntity implements Serializable {
    /**
     * 简历主键 id
     **/
    private Long id;

    /**
     * 姓名 name
     **/
    private String name;

    /**
     * 年龄 age
     **/
    private Integer age;

    /**
     * 性别 sex
     **/
    private Integer sex;

    /**
     * 现居住地 address
     **/
    private String address;

    /**
     * 手机号码 phone
     **/
    private String phone;

    /**
     * 个人邮箱 email
     **/
    private String email;

    /**
     * 出生日期 birthday
     **/
    private Date birthday;

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
     * 学历 枚举 education
     **/
    private String education;

    /**
     * 应聘职位 position_applied
     **/
    private String positionApplied;

    /**
     * 工作地点或者城市 working_place
     **/
    private String workingPlace;

    /**
     * 期望薪资 salary
     **/
    private String salary;

    /**
     * 工作年限 work_experience
     **/
    private Float workExperience;

    /**
     * 发件人邮箱 send_mail
     **/
    private String sendMail;

    /**
     * 发件人邮箱时间 send_time
     **/
    private Date sendTime;

    /**
     * 邮箱标题 email_title
     **/
    private String emailTitle;

    /**
     * 发送mq状态 0:成功 1：失败 status
     **/
    private Integer status;

    /**
     * 部门ID dept_id
     **/
    private Long deptId;

    /**
     * 部门名称 dept_name
     **/
    private String deptName;

    /**
     * 收集邮件邮箱iD create_email_id
     **/
    private Long createEmailId;

    /**
     * 创建人邮箱 create_email
     **/
    private String createEmail;

    /**
     * 赛道id pro_id
     **/
    private Long proId;

    /**
     * 赛道（产品名称） pro_name
     **/
    private String proName;

    /**
     * 公司ID company_id
     **/
    private Long companyId;

    /**
     * 公司ID company_name
     **/
    private String companyName;

    /**
     * 来源ID source_id
     **/
    private Long sourceId;

    /**
     * 来源 source_name
     **/
    private String sourceName;

    /**
     * 推广人Id gen_user_id
     **/
    private Long genUserId;

    /**
     * 推广人名称 gen_user_name
     **/
    private String genUserName;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 开始同步时间 sync_time
     **/
    private Date syncTime;

    /**
     * 邮箱内容 resume_detail
     **/
    private String resumeDetail;

    private static final long serialVersionUID = 1L;
}