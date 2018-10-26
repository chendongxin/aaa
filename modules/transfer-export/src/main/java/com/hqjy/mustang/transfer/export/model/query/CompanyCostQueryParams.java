package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广公司费用报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广公司费用报表高级查询参数")
public class CompanyCostQueryParams implements Serializable {

    @ApiModelProperty(value = "开始日期（必传）")
    @NotEmpty(message = "日期不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必传）")
    @NotEmpty(message = "日期不能为空")
    private String endTime;

    @ApiModelProperty(value = "部门ID（必传）")
    @NotNull(message = "部门ID不能为空")
    private Long deptId;

    @ApiModelProperty(value = "部门名称（必传）")
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "推广公司ID（必传）")
    @NotNull(message = "推广公司ID不能为空")
    private Long companyId;

    @ApiModelProperty(value = "推广公司名称（必传）")
    @NotEmpty(message = "推广公司名称不能为空")
    private String companyName;

    @ApiModelProperty(value = "获取方式ID（必传）,见数字典GET_WAY:1-主动，2-被动")
    @NotNull(message = "获取方式不能为空")
    private Integer getWay;

    @ApiModelProperty(value = "费用类型ID（必传）,见数字典COST_TYPE:1-人民币，2-虚拟币")
    @NotNull(message = "费用类型ID不能为空")
    private Integer costType;

    @ApiModelProperty(value = "来源平台ID（必传）")
    @NotNull(message = "来源平台ID不能为空")
    private Long sourceId;

    @ApiModelProperty(value = "来源平台名称（必传）")
    @NotEmpty(message = "来源平台名称不能为空")
    private String sourceName;
}
