package com.hqjy.mustang.transfer.crm.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransferCustomerMsgBodyDTO {

    /**
     * 赛道ID
     */
    private Long proId;

    /**
     * 赛道名称
     */
    private String proName;

    /**
     * 推广公司ID
     */
    private Long companyId;

    /**
     * 推广公司名称
     */
    private String companyName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 分配ip
     */
    private String ip;

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * 分配人员ID
     */
    private Long userId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 来源平台ID
     */
    private Long sourceId;

    /**
     * 来源平台名称
     */
    private String sourceName;

    /**
     * 获取方式 (1-主动获取，2-被动获取)
     */
    private Byte getWay;

    /**
     * 分配规则，是否不分配， 默认false分配，true 不自动分配
     */
    private Boolean notAllot;

    /**
     * NC客户编号 nc_id
     **/
    private String ncId;

    /**
     * 客户状态 (1-(失败)有效，2-(失败)无效，3-预约，4-成交) status
     **/
    private Integer status;

    /**
     * 创建人编号 create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人编号 create_user_name
     **/
    private String createUserName;

    /**
     * 创建人，主要针对小能
     */
    private String creator;

    /**
     * 客户手机号码
     **/
    private String phone;

    /**
     * 座机
     **/
    private String landLine;

    /**
     * qq
     */
    private String qq;

    /**
     * 微信
     */
    private String weChat;

    /**
     * 客户姓名 name
     **/
    private String name;

    /**
     * 性别(-1：女   0：未知   1：男) sex
     **/
    private Integer sex;

    /**
     * 年龄 age
     **/
    private Byte age;

    /**
     * 邮箱 email
     **/
    private String email;

    /**
     * 期望职位 position_applied
     **/
    private String positionApplied;

    /**
     * 期望工作地点 working_place
     **/
    private String workingPlace;

    /**
     * 毕业学校 school
     **/
    private String school;

    /**
     * 学历名称(数据来源于transfer_education学历表) education_name
     **/
    private String educationName;

    /**
     * 专业 major
     **/
    private String major;

    /**
     * 工作经验 work_experience
     **/
    private Byte workExperience;

    /**
     * 注释 memo
     **/
    private String note;

    private static final long serialVersionUID = 1L;
}
