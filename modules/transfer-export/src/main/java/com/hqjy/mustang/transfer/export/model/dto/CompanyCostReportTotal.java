package com.hqjy.mustang.transfer.export.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018年10月22日15:10:44
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广公司费用报表数据对象")
public class CompanyCostReportTotal {

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "合计总费用")
    private String totalCost="0.0000";

    @ApiModelProperty(value = "合计商机量")
    private int num;

    @ApiModelProperty(value = "推广方式费用集合")
    private List<TransferGenWayCost> genWayCosts;
}
