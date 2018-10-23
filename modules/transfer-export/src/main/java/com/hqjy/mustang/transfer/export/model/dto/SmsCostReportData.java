package com.hqjy.mustang.transfer.export.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.annotation.ExcelAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author xyq
 * @date create on 2018年10月20日16:16:14
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转短信费用报表数据对象")
public class SmsCostReportData {

    @ApiModelProperty(value = "序号")
    @ExcelAttribute(name = "序号", column = "A")
    private Integer sequence;


    @ApiModelProperty(value = "部门Id", hidden = true)
    @JSONField(serialize = false)
    private Long deptId;

    @ApiModelProperty(value = "部门")
    @ExcelAttribute(name = "部门", column = "B")
    private String deptName;

    @ApiModelProperty(value = "发送成功次数")
    @ExcelAttribute(name = "发送成功次数", column = "C")
    private int sendSuccessNum;

    @ApiModelProperty(value = "总消耗条数")
    @ExcelAttribute(name = "总消耗条数", column = "D")
    private int sendNum;

    @ApiModelProperty(value = "总费用")
    @ExcelAttribute(name = "总费用", column = "E")
    private String cost;



}
