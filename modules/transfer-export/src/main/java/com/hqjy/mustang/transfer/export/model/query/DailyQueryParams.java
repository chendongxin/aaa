package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广日常数据报表高级查询参数
 */
@Data
@ApiModel(value = "推广日常数据报表高级查询参数")
public class DailyQueryParams {

    @ApiModelProperty(value = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "推广公司")
    private Long companyId;

    @ApiModelProperty(value = "来源平台")
    private Long sourceId;

    @ApiModelProperty(value = "获取方式")
    private Long getWay;
}
