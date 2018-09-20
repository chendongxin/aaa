package com.hqjy.mustang.transfer.export.model.dto;

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
@ApiModel(value = "招转日常报表数据合计对象")
public class DailyReportTotal {


    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "B")
    private String deptName;

    @ApiModelProperty(value = "商机总量")
    @ExcelAttribute(name = "商机总量", column = "C")
    private int businessNum;

    @ApiModelProperty(value = "已跟进商机量")
    @ExcelAttribute(name = "已跟进商机量", column = "D")
    private int followNum;

    @ApiModelProperty(value = "有效商机量")
    @ExcelAttribute(name = "有效商机量", column = "E")
    private int validNum;

    @ApiModelProperty(value = "有效商机率")
    @ExcelAttribute(name = "有效商机率", column = "F")
    private String validRate;

    @ApiModelProperty(value = "非失败商机量")
    @ExcelAttribute(name = "非失败商机量", column = "G")
    private int unFailNum;

    @ApiModelProperty(value = "非失败率")
    @ExcelAttribute(name = "非失败率", column = "H")
    private String unFailRate;

    @ApiModelProperty(value = "商机上门量")
    @ExcelAttribute(name = "商机上门量", column = "I")
    private int visitNum;

    @ApiModelProperty(value = "有效上门量")
    @ExcelAttribute(name = "有效上门量", column = "J")
    private int visitValidNum;

    @ApiModelProperty(value = "有效上门率")
    @ExcelAttribute(name = "有效上门率", column = "K")
    private String visitValidRate;

    @ApiModelProperty(value = "意向量")
    @ExcelAttribute(name = "意向量", column = "L")
    private int intentionNum;

    @ApiModelProperty(value = "上门意向率")
    @ExcelAttribute(name = "上门意向率", column = "M")
    private String visitIntentionRate;

    @ApiModelProperty(value = "成交量")
    @ExcelAttribute(name = "成交量", column = "N")
    private int dealNum;

    @ApiModelProperty(value = "上门成交率")
    @ExcelAttribute(name = "上门成交率", column = "O")
    private String visitDealRate;

}
