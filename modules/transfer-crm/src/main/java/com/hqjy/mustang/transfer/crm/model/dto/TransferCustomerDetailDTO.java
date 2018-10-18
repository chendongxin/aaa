package com.hqjy.mustang.transfer.crm.model.dto;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author gmm
 * @ description 客户基本资料显示
 * @ date create in 2018/9/25 10:37
 */
@Data
@Accessors(chain = true)
public class TransferCustomerDetailDTO {

    /**
     * 客户编号 customer_id
     **/
    private Long customerId;

    /**
     * 客户姓名 name
     **/
    @ExcelAttribute(name = "姓名")
    private String name;

    /**
     * 性别
     **/
    @ExcelAttribute(name = "性别")
    private String gender;

    /**
     * 年龄
     */
    @ExcelAttribute(name = "年龄")
    private String year;

    /**
     * 手机
     */
    @ExcelAttribute(name = "电话",prompt = "手机号码必填")
    private String phone;

    /**
     * 邮箱 email
     **/
    @ExcelAttribute(name = "电子邮箱")
    private String email;

    /**
     * 期望职位 position_applied
     **/
    @ExcelAttribute(name = "应聘岗位")
    private String positionApplied;

    /**
     * 期望工作地点 working_place
     **/
    @ExcelAttribute(name = "期望工作地点")
    private String workingPlace;

    /**
     * 毕业学校 school
     **/
    @ExcelAttribute(name = "毕业院校")
    private String school;

    /**
     * 学历 education
     **/
    @ExcelAttribute(name = "学历")
    private String education;

    /**
     * 专业 major
     **/
    @ExcelAttribute(name = "专业")
    private String major;

    /**
     * 工作经验 experience
     **/
    @ExcelAttribute(name = "经验")
    private String experience;

    /**
     * 备注 note
     **/
    @ExcelAttribute(name = "备注")
    private String note;

    /**
     * 性别:见数据字典SEX枚举(0-未知，1-男，2-女) sex
     **/
    private Integer sex;

    /**
     * 年龄 age
     **/
    private Byte age;

    /**
     * 地址 address
     **/
    private String address;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 毕业年份 graduate_date
     **/
    private Date graduateDate;

    /**
     * 学历ID education_id (0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士，7-博士)
     **/
    private Long educationId;

    /**
     * 应聘类别 apply_type (1-开发类，2-设计类，3-运营类，4-产品类，5-技术支持类，6-其它)
     **/
    private Integer applyType;

    /**
     * 应聘关键词 apply_key
     **/
    private Integer applyKey;

    /**
     * 工作经验 work_experience
     **/
    private Byte workExperience;

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
     * 客户状态,BIZ_STATUS(0-潜在，1-(失败)有效，2-(失败)无效，3-预约，4-成交) status
     **/
    private Integer status;

    /**
     * 推广方式：见数据字典GET_WAY枚举(1-主动获取，2-被动获取) get_way
     **/
    private Byte getWay;

    /**
     * 客户联系方式 (冗余)
     */
    List<TransferCustomerContactEntity> contactList;

    private static final long serialVersionUID = 1L;
}
