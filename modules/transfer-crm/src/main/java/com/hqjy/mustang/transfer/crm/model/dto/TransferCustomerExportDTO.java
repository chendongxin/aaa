package com.hqjy.mustang.transfer.crm.model.dto;


import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author xieyuqing
 * @ description 客户列表导出实体
 * @ date create in 2018/6/8 10:28
 */
@Data
@Accessors(chain = true)
public class TransferCustomerExportDTO {

    /**
     * 客户编号
     **/
    private Long customerId;

    @ExcelAttribute(name = "客户姓名")
    private String name;

    @ExcelAttribute(name = "性别")
    private String gender;

    @ExcelAttribute(name = "手机")
    private String phone;

    @ExcelAttribute(name = "qq")
    private String qq;

    @ExcelAttribute(name = "微信")
    private String weiXin;

    @ExcelAttribute(name = "座机")
    private String landLine;

    @ExcelAttribute(name = "年龄")
    private String age;

    @ExcelAttribute(name = "部门")
    private String deptName;

    /**
     * 学历名称(数据来源于transfer_education学历表) education_name
     **/
    @ExcelAttribute(name = "学历")
    private String educationName;

    /**
     * 工作经验 work_experience
     **/
    @ExcelAttribute(name = "工作经验")
    private Byte workExperience;

    /**
     * 应聘类别 apply_type
     **/
    @ExcelAttribute(name = "应聘类别")
    private String applyType;

    /**
     * 应聘关键词 apply_key
     **/
    @ExcelAttribute(name = "应聘关键词")
    private String applyKey;

    /**
     * 期望职位 position_applied
     **/
    @ExcelAttribute(name = "应聘职位")
    private String positionApplied;

    /**
     * 专业 major
     **/
    @ExcelAttribute(name = "专业")
    private String major;

    @ExcelAttribute(name = "首次归属人")
    private String firstUserName;

    /**
     * 该条商机全部通话次数，只统计一共接通多少次，未接听不计在内
     */
    @ExcelAttribute(name = "跟进次数")
    private int followCount;

    @ExcelAttribute(name = "跟进状态")
    private String followStatus;

    @ExcelAttribute(name = "归属人")
    private String userName;

    /**
     * 推广方式：见数据字典GET_WAY枚举(1-主动获取，2-被动获取) get_way
     **/
    @ExcelAttribute(name = "推广方式")
    private Byte getWay;

    /**
     * 客户来源 source_id
     **/
    @ExcelAttribute(name = "客户来源")
    private String sourceName;

    /**
     * 推广公司 company_id
     **/
    @ExcelAttribute(name = "推广公司")
    private String companyName;

    /**
     * 客户状态,BIZ_STATUS(0-潜在，1-(失败)有效，2-(失败)无效，3-预约，4-成交) status
     **/
    @ExcelAttribute(name = "客户状态")
    private int status;

    /**
     * 是否上门
     */
    @ExcelAttribute(name = "是否上门")
    private String visit;

    /**
     * 是否意向
     */
    @ExcelAttribute(name = "是否意向")
    private String intention;

    /**
     * 是否成交
     */
    @ExcelAttribute(name = "是否成交")
    private String deal;

    @ExcelAttribute(name = "日期")
    private Date createTime;

    @ExcelAttribute(name = "备注")
    private String memo;
    /**
     * 性别(-1：女   0：未知   1：男) sex
     **/
    private Integer sex;
}
