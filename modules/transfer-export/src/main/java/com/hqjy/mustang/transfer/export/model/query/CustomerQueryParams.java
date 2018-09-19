package com.hqjy.mustang.transfer.export.model.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广报表高级查询参数")
public class CustomerQueryParams {

    @ApiModelProperty(value = "日期（必选）")
    private String date;

    @ApiModelProperty(value = "赛道")
    private Long productId;

    @ApiModelProperty(value = "推广公司")
    private Long companyId;

    @ApiModelProperty(value = "来源平台")
    private Long sourceId;

    @ApiModelProperty(value = "获取方式")
    private Long getWay;

    @ApiModelProperty(value = "客服人员")
    private int userId;
}
