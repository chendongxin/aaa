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
@ApiModel(value = "电销商机拨打排行报表数据对象")
public class SellCallReportTotal {

    @ApiModelProperty(value = "电销专员")
    @ExcelAttribute(name = "电销专员", column = "A")
    private String sellName;

    @ApiModelProperty(value = "部门Id", hidden = true)
    private Long deptId;

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "B")
    private String deptName;

    @ApiModelProperty(value = "拨打量")
    @ExcelAttribute(name = "拨打量", column = "C")
    private int callNum;

    @ApiModelProperty(value = "接通量")
    @ExcelAttribute(name = "接通量", column = "D")
    private int connectNum;

    @ApiModelProperty(value = "接通率")
    @ExcelAttribute(name = "接通率", column = "E")
    private String connectRate;

    @ApiModelProperty(value = "通话时长")
    @ExcelAttribute(name = "通话时长", column = "F")
    private String duration="00:00:00";
}
