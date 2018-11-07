package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "电销专员排行报表对象")
public class SellAttacheReportData {


    @ApiModelProperty(value = "电销专员Id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "电销专员")
    @ExcelAttribute(name = "电销专员", column = "A")
    private String name;

    @ApiModelProperty(value = "部门Id", hidden = true)
    private Long deptId;

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "B")
    private String deptName;

    @ApiModelProperty(value = "商机量")
    @ExcelAttribute(name = "商机量", column = "C")
    private int businessNum;

    @ApiModelProperty(value = "分配商机量")
    @ExcelAttribute(name = "分配商机量", column = "D")
    private int allotNum;

    @ApiModelProperty(value = "商机有效量")
    @ExcelAttribute(name = "商机有效量", column = "E")
    private int validNum;

    @ApiModelProperty(value = "预约量")
    @ExcelAttribute(name = "预约量", column = "F")
    private int reservationNum;

    @ApiModelProperty(value = "有效上门量")
    @ExcelAttribute(name = "预约量", column = "G")
    private int visitValidNum;

    @ApiModelProperty(value = "上门量")
    @ExcelAttribute(name = "上门量", column = "H")
    private int visitNum;

    @ApiModelProperty(value = "成交量")
    @ExcelAttribute(name = "成交量", column = "I")
    private int dealNum;

    @ApiModelProperty(value = "商机有效率")
    @ExcelAttribute(name = "商机有效率", column = "J")
    private String validRate;

    @ApiModelProperty(value = "实际上门率")
    @ExcelAttribute(name = "实际上门率", column = "K")
    private String visitRate;

    @ApiModelProperty(value = "有效商机上门率")
    @ExcelAttribute(name = "有效商机上门率", column = "L")
    private String visitValidRate;


}
