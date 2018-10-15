package com.hqjy.mustang.transfer.export.model.dto;


import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gmm
 * @ description 客户基本资料显示
 * @ date create in 2018/10/12 10:28
 */
@Data
@Accessors(chain = true)
public class CustomerDetailDTO {

    /**
     * 客户姓名 name
     **/
    @ExcelAttribute(name = "姓名")
    private String name;

    /**
     * 年龄 age
     **/
    @ExcelAttribute(name = "年龄")
    private String age;

    /**
     * 性别
     **/
    @ExcelAttribute(name = "性别")
    private String gender;

    /**
     * 邮箱 email
     **/
    @ExcelAttribute(name = "电子邮箱")
    private String email;

    /**
     * 手机
     */
    @ExcelAttribute(name = "电话",prompt = "手机号码必填")
    private String phone;

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
     * 专业 major
     **/
    @ExcelAttribute(name = "专业")
    private String major;

    /**
     * 学历ID education_id (0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士，7-博士)
     **/
    @ExcelAttribute(name = "学历")
    private String educationId;

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
}
