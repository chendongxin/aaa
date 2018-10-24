package com.hqjy.mustang.transfer.export.model.dto;

import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xyq
 * @date create on 2018年10月22日15:10:44
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广公司费用报表数据合计对象")
public class CompanyCostReportTotal {

    @ApiModelProperty(value = "总费用")
    @ExcelAttribute(name = "总费用", column = "B")
    private BigDecimal totalCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "商机量")
    @ExcelAttribute(name = "商机量", column = "C")
    private int num;

    @ApiModelProperty(value = "精品(帮帮)")
    @ExcelAttribute(name = "精品(帮帮)", column = "D")
    private BigDecimal boutiqueCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "精准(黄金展位)")
    @ExcelAttribute(name = "精准(黄金展位)", column = "E")
    private BigDecimal precisionCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "刷新")
    @ExcelAttribute(name = "刷新", column = "F")
    private BigDecimal flushCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "职位发布")
    @ExcelAttribute(name = "职位发布", column = "G")
    private BigDecimal jobPostingCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "置顶")
    @ExcelAttribute(name = "置顶", column = "H")
    private BigDecimal toppingCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "下载")
    @ExcelAttribute(name = "下载", column = "I")
    private BigDecimal loadCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "广告")
    @ExcelAttribute(name = "广告", column = "J")
    private BigDecimal adCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

    @ApiModelProperty(value = "优先推送")
    @ExcelAttribute(name = "优先推送", column = "K")
    private BigDecimal pushCost = BigDecimal.ZERO.setScale(4, BigDecimal.ROUND_HALF_UP);

}
