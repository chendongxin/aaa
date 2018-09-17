package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广报表数据对象
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广报表数据对象")
public class PromotionReportData {

    @ApiModelProperty(value = "序号")
    private Integer sequence;

    @ApiModelProperty(value = "部门Id", hidden = true)
    private Long deptId;

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "A")
    private String deptName;

    @ApiModelProperty(value = "日商机量")
    @ExcelAttribute(name = "日商机量", column = "B")
    private int dayBusinessNum;

    @ApiModelProperty(value = "日有效商机量")
    @ExcelAttribute(name = "日有效商机量", column = "D")
    private int dayValidNum;

    @ApiModelProperty(value = "日商机有效率")
    @ExcelAttribute(name = "日商机有效率", column = "E")
    private String dayValidRate;

    @ApiModelProperty(value = "日有效上门量")
    @ExcelAttribute(name = "日有效上门量", column = "F")
    private int dayValidVisitNum;

    @ApiModelProperty(value = "分配电销数")
    @ExcelAttribute(name = "分配电销数", column = "G")
    private int dayAllotSaleNum;

    @ApiModelProperty(value = "电销日均商机")
    @ExcelAttribute(name = "电销日均商机", column = "H")
    private int dayAllotAvgNum;

    @ApiModelProperty(value = "月商机量")
    @ExcelAttribute(name = "月商机量", column = "I")
    private int monthBusinessNum;

    @ApiModelProperty(value = "月有效商机量")
    @ExcelAttribute(name = "月有效商机量", column = "J")
    private int monthValidNum;

    @ApiModelProperty(value = "月有效上门量")
    @ExcelAttribute(name = "月有效上门量", column = "K")
    private int monthValidVisitNum;

    @ApiModelProperty(value = "意向量")
    @ExcelAttribute(name = "意向量", column = "L")
    private int monthIntentionNum;

    @ApiModelProperty(value = "上门意向率")
    @ExcelAttribute(name = "上门意向率", column = "M")
    private int monthVisitIntentionRate;

    @ApiModelProperty(value = "成交量")
    @ExcelAttribute(name = "成交量", column = "N")
    private int monthDealNum;

    @ApiModelProperty(value = "上门率")
    @ExcelAttribute(name = "上门率", column = "O")
    private int visitRate;

    @ApiModelProperty(value = "月费(RMB)")
    @ExcelAttribute(name = "月费(RMB)", column = "P")
    private int monthCost;

    @ApiModelProperty(value = "有效商机成本")
    @ExcelAttribute(name = "有效商机成本", column = "Q")
    private int monthValidCost;

    @ApiModelProperty(value = "上门成本")
    @ExcelAttribute(name = "上门成本", column = "R")
    private int monthIntentionCost;

    @ApiModelProperty(value = "成交成本")
    @ExcelAttribute(name = "成交成本", column = "S")
    private int monthDealCost;

    @ApiModelProperty(value = "回款总额")
    @ExcelAttribute(name = "回款总额", column = "T")
    private int monthPaybackCost;

}
