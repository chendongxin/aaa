package com.hqjy.mustang.allot.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_customer 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/14 10:08
 */
@Data
@Accessors(chain = true)
public class TransferAllotCustomerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean notAllot;
    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * NC客户编号 nc_id
     **/
    private String ncId;

    /**
     * 产品 pro_id
     **/
    private Long proId;

    /**
     * 赛道（产品)名称 pro_name
     **/
    private String proName;

    /**
     * 客户来源 source_id
     **/
    private Long sourceId;

    /**
     * 推广公司 company_id
     **/
    private Long companyId;

    /**
     * 名称 name
     **/
    private String name;

    /**
     * 客户状态,BIZ_STATUS(0-潜在，1-(失败)有效，2-(失败)无效，3-预约，4-成交) status
     **/
    private Integer status;

    /**
     * 推广方式：见数据字典GET_WAY枚举(1-主动获取，2-被动获取) get_way
     **/
    private Integer getWay;

    /**
     * 当前跟进人ID user_id
     **/
    private Long userId;

    /**
     * 当前跟进人名称 user_name
     **/
    private String userName;

    /**
     * 当前跟进人部门ID dept_id
     **/
    private Long deptId;

    /**
     * 部门名称 dept_name
     **/
    private String deptName;

    /**
     * 首次跟进人 first_user_id
     **/
    private Long firstUserId;

    /**
     * 首次跟进人名称 first_user_name
     **/
    private String firstUserName;

    /**
     * 最后跟进人 last_user_id
     **/
    private Long lastUserId;

    /**
     * 最后跟进人名称 last_user_name
     **/
    private String lastUserName;

    /**
     * 推广用户ID gen_user_id
     **/
    private Long genUserId;

    /**
     * 推广用户名称 gen_user_name
     **/
    private String genUserName;

    /**
     * 手机号码 phone
     **/
    private String phone;

    /**
     * 微信 we_chat
     **/
    private String weChat;

    /**
     * qq qq
     **/
    private String qq;

    /**
     * 座机 land_line
     **/
    private String landLine;

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
     * 分配时间 create_time
     **/
    private Date allotTime;

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

    /************************************/
    /**
     * 年龄 age
     **/
    private Integer age;

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
     * 学历名称
     **/
    private String education;

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
    private Float workExperience;

    /**
     * 备注 note
     **/
    private String note;

    /**
     * 简历详情 resume_detail
     **/
    private String resumeDetail;

}