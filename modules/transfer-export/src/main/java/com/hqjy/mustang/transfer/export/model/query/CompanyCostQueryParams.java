package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广公司费用报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广公司费用报表高级查询参数")
public class CompanyCostQueryParams {

    @ApiModelProperty(value = "开始日期（必选）")
    @NotBlank(message = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    @NotBlank(message = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门ID")
    @NotBlank(message = "部门（必选）")
    private Long deptId;

    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "推广公司")
    @NotBlank(message = "推广公司（必选）")
    private Long companyId;

    @ApiModelProperty(value = "获取方式")
    @NotBlank(message = "获取方式（必选）")
    private Long getWay;

    @ApiModelProperty(value = "费用类型")
    @NotBlank(message = "费用类型（必选）")
    private Long costType;

    @ApiModelProperty(value = "来源平台")
    @NotBlank(message = "来源平台（必选）")
    private Long sourceId;

}
