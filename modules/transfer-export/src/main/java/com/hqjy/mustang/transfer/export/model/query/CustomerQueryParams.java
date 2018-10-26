package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广报表高级查询参数")
public class CustomerQueryParams {


    @ApiModelProperty(value = "开始日期（必选）")
    @NotEmpty(message = "日期不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    @NotEmpty(message = "日期不能为空")
    private String endTime;

    @ApiModelProperty(value = "客服人员")
    private Long userId;

    @JSONField(serialize = false)
    private String userIds;

    @ApiModelProperty(value = "推广公司")
    private Long companyId;

    @ApiModelProperty(value = "来源平台")
    private Long sourceId;


}
