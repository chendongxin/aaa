package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gmm
 * @date:2018/10/18 11:40
 * @apiNote 招转短信费用报表高级查询参数
 */
@Data
@ApiModel(value = "招转短信费用报表高级查询参数")
public class NoteCostQueryParams {

    @ApiModelProperty(value = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @JSONField(serialize = false)
    private String deptIds;

}
