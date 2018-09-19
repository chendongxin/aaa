package com.hqjy.mustang.common.model.crawler;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_resume 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/13 16:43
 */
@Data
public class TransferResume implements Serializable {
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
    private String age;

    /**
     * 性别 sex
     **/
    private Byte sex;

    /**
     * 手机号码 phone
     **/
    private String phone;

    /**
     * 毕业学校 school
     **/
    private String school;

    /**
     * 工作年限 working
     **/
    private String working;

    /**
     * 工作地点或者城市 address
     **/
    private String address;

    /**
     * 学历 education
     **/
    private String education;

    /**
     * 个人邮箱 email
     **/
    private String email;

    /**
     * 应聘职位 job
     **/
    private String job;

    /**
     * 现居住地 area
     **/
    private String area;

    /**
     * 毕业年份 graduation_year
     **/
    private Date graduationYear;

    /**
     * 期望薪资 salary
     **/
    private String salary;

    /**
     * 专业 profession
     **/
    private String profession;

    /**
     * 出生日期 birthday
     **/
    private Date birthday;

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
     * 创建人Id create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人名称 create_user_name
     **/
    private String createUserName;

    /**
     * 收集邮件邮箱iD create_email_id
     **/
    private Long createEmailId;

    /**
     * 创建人邮箱 create_email
     **/
    private String createEmail;

    /**
     * 赛道id create_pro_id
     **/
    private Long createProId;

    /**
     * 赛道（产品名称） create_pro_name
     **/
    private String createProName;

    /**
     * 公司ID create_company_id
     **/
    private Long createCompanyId;

    /**
     * 公司ID create_company_name
     **/
    private String createCompanyName;

    /**
     * 来源ID create_source_id
     **/
    private Long createSourceId;

    /**
     * 来源 create_source_name
     **/
    private String createSourceName;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 邮箱内容 email_body
     **/
    private String emailBody;

    private static final long serialVersionUID = 1L;
}