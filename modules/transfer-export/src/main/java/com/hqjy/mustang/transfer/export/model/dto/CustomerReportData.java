package com.hqjy.mustang.transfer.export.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "客服推广报表数据对象")
public class CustomerReportData {

    @ApiModelProperty(value = "序号")
    @ExcelAttribute(name = "序号", column = "A")
    private Integer sequence;

    @ApiModelProperty(value = "客服用户Id", hidden = true)
    @JSONField(serialize = false)
    private Long userId;

    @ApiModelProperty(value = "姓名")
    @ExcelAttribute(name = "姓名", column = "B")
    private String name;

    @ApiModelProperty(value = "部门Id", hidden = true)
    @JSONField(serialize = false)
    private Long deptId;

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "C")
    private String deptName;

    @ApiModelProperty(value = "商机总量")
    @ExcelAttribute(name = "商机总量", column = "D")
    private int businessNum;

    @ApiModelProperty(value = "有效商机量")
    @ExcelAttribute(name = "有效商机量", column = "E")
    private int validNum;

    @ApiModelProperty(value = "商机上门量")
    @ExcelAttribute(name = "商机上门量", column = "F")
    private int visitNum;

    @ApiModelProperty(value = "有效上门量")
    @ExcelAttribute(name = "有效上门量", column = "G")
    private int visitValidNum;

    @ApiModelProperty(value = "成交量")
    @ExcelAttribute(name = "成交量", column = "H")
    private int dealNum;



}
