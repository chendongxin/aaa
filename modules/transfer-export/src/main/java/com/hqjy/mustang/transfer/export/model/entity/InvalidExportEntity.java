package com.hqjy.mustang.transfer.export.model.entity;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 无效客户报表导出实体类
 *
 * @author : gmm
 * @date : 2018年10月11日14:17:57
 */
@Data
@Accessors(chain = true)
public class InvalidExportEntity implements Serializable {

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门")
    private String deptName;

    @ApiModelProperty(value = "姓名")
    @ExcelAttribute(name = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    @ExcelAttribute(name = "年龄")
    private String age;

    @ApiModelProperty(value = "联系电话")
    @ExcelAttribute(name = "联系电话")
    private String phone;

    @ApiModelProperty(value = "学历")
    @ExcelAttribute(name = "学历")
    private String education;

    @ApiModelProperty(value = "工作经验")
    @ExcelAttribute(name = "工作经验")
    private String experience;

    @ApiModelProperty(value = "应聘职位关键词")
    @ExcelAttribute(name = "应聘职位关键词")
    private String jobKey;

//    @ApiModelProperty(value = "首次预约时间")
//    @ExcelAttribute(name = "首次预约时间")
//    private String firstReservationTime;
//
//    @ApiModelProperty(value = "末次预约时间")
//    @ExcelAttribute(name = "末次预约时间")
//    private String lastReservationTime;
//
//    @ApiModelProperty(value = "预约次数")
//    @ExcelAttribute(name = "预约次数")
//    private String reservationCount;

    @ApiModelProperty(value = "失败类型")
    @ExcelAttribute(name = "失败类型")
    private String status;

    @ApiModelProperty(value = "创建者")
    @ExcelAttribute(name = "创建者")
    private String createUserName;

    @ApiModelProperty(value = "创建时间")
    @ExcelAttribute(name = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "归属人")
    @ExcelAttribute(name = "归属人")
    private String userName;

    @ApiModelProperty(value = "商机获取方式")
    @ExcelAttribute(name = "商机获取方式")
    private String getWay;

    @ApiModelProperty(value = "来源平台")
    @ExcelAttribute(name = "来源平台")
    private String source;

    @ApiModelProperty(value = "推广公司")
    @ExcelAttribute(name = "来源平台")
    private String company;

    @ApiModelProperty(value = "专业")
    @ExcelAttribute(name = "专业")
    private String profession;

    @ApiModelProperty(value = "应聘岗位")
    @ExcelAttribute(name = "应聘岗位")
    private String applyPosition;

}
