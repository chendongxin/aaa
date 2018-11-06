package com.hqjy.mustang.transfer.export.model.query;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * @author gmm
 * @date create on 2018年10月22日15:25:23
 * @apiNote 电销专员排行报表
 */
@Data
@ApiModel(value = "电销报表查询参数")
public class SellQueryParams {

    @ApiModelProperty(value = "开始日期（必传）")
    @NotEmpty(message = "日期不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必传）")
    @NotEmpty(message = "日期不能为空")
    private String endTime;

    @ApiModelProperty(value = "部门ID（必传）")
    @NotNull(message = "部门ID不能为空")
    private Long deptId;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "获取方式")
    private Long getWay;

    @ApiModelProperty(value = "来源平台")
    private Long sourceId;

    @ApiModelProperty(value = "电销人员")
    private Long userId;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String userIds;
}
