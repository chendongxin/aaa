package com.hqjy.mustang.transfer.export.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
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
@ApiModel(value = "部门电销排行报表数据对象")
public class SellDeptReportTotal {


    @ApiModelProperty(value = "商机量")
    @ExcelAttribute(name = "商机量", column = "B")
    private int businessNum;

    @ApiModelProperty(value = "商机有效量")
    @ExcelAttribute(name = "商机有效量", column = "C")
    private int validNum;

    @ApiModelProperty(value = "预约量")
    @ExcelAttribute(name = "预约量", column = "D")
    private int reservationNum;

    @ApiModelProperty(value = "上门量")
    @ExcelAttribute(name = "上门量", column = "E")
    private int visitNum;

    @ApiModelProperty(value = "成交量")
    @ExcelAttribute(name = "成交量", column = "F")
    private int dealNum;

    @ApiModelProperty(value = "有效上门量", hidden = true)
    @JSONField(serialize = false)
    private int visitValidNum;

    @ApiModelProperty(value = "商机有效率")
    @ExcelAttribute(name = "商机有效率", column = "G")
    private String validRate;

    @ApiModelProperty(value = "实际上门率")
    @ExcelAttribute(name = "实际上门率", column = "H")
    private String visitRate;

    @ApiModelProperty(value = "有效商机上门率")
    @ExcelAttribute(name = "有效商机上门率", column = "I")
    private String visitValidRate;
}
