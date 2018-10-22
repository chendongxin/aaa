package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广公司费用报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广公司费用报表高级查询参数")
public class CompanyCostQueryParams {


    @ApiModelProperty(value = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "推广公司")
    private Long companyId;

    @ApiModelProperty(value = "获取方式")
    private Long getWay;

    @ApiModelProperty(value = "费用类型")
    private Long costType;

    @ApiModelProperty(value = "来源平台")
    private Long sourceId;

}
