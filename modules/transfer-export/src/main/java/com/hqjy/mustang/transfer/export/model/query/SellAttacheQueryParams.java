package com.hqjy.mustang.transfer.export.model.query;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gmm
 * @date create on 2018年10月22日15:25:23
 * @apiNote 电销专员排行报表
 */
@Data
@ApiModel(value = "电销专员排行报表查询参数")
public class SellAttacheQueryParams {

    @ApiModelProperty(value = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "获取方式")
    private Long getWay;
}
