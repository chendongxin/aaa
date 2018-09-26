package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TransferCustomerDTO {

    /**
     * 客户编号
     **/
    private Long customerId;

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
     * 当前跟进人部门ID dept_id
     **/
    private Long deptId;

    /**
     * 推广公司 company_id
     **/
    private Long companyId;

    /**
     * 客户来源 source_id
     **/
    private Long sourceId;

    /**
     * 赛道ID pro_id
     */
    private Long proId;

    /**
     * 首次跟进人 first_user_id
     **/
    private Long firstUserId;

    /**
     * 姓名 name
     **/
    private String name;

    /**
     * 性别:见数据字典SEX枚举(0-未知，1-男，2-女) sex
     **/
    private Integer sex;

    /**
     * 年龄 age
     **/
    private Byte age;

    /**
     * 学历ID(数据来源于transfer_education学历表) education_id
     **/
    private Long educationId;

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
     * 应聘类别 apply_type
     **/
    private String applyType;

    /**
     * 期望职位 position_applied
     **/
    private String positionApplied;

    /**
     * 期望工作地点 working_place
     **/
    private String workingPlace;

    /**
     * 应聘关键词 apply_key
     **/
    private String applyKey;

    /**
     * 工作经验 work_experience
     **/
    private Byte workExperience;

    /**
     * 推广方式：见数据字典GET_WAY枚举(1-主动获取，2-被动获取) get_way
     **/
    private Byte getWay;

    /**
     * 备注 note
     **/
    private String note;

    /**
     * 失败状态 status（1-(失败)有效，2-（失败）无效
     */
    private Integer status;

    /**
     * 失败原因 memo
     */
    private String memo;

}
