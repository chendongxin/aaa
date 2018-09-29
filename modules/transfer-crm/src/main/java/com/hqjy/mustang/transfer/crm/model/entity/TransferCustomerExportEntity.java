package com.hqjy.mustang.transfer.crm.model.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * transfer_customer 客户主表实体类
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Data
@Accessors(chain = true)
public class TransferCustomerExportEntity {

    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * 名称 name
     **/
    private String name;

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
     * 推广方式：见数据字典GET_WAY枚举(1-主动获取，2-被动获取) get_way
     **/
    private Byte getWay;

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

    /**
     * 当前跟进人ID user_id
     */
    private Long userId;

    /**
     * 当前跟进人姓名
     */
    private String userName;

    /**
     * 当前跟进人部门ID
     */
    private Long deptId;

    /**
     * 当前跟进人部门名称
     */
    private String deptName;

    /**
     * 来源平台ID
     */
    private Long sourceId;

    /**
     * 来源平台名称
     */
    private String sourceName;

    /**
     * 推广公司ID
     */
    private Long companyId;

    /**
     * 推广公司名称
     */
    private String companyName;

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

    private static final long serialVersionUID = 1L;
}
