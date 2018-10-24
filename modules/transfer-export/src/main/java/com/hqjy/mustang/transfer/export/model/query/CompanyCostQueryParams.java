package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转推广公司费用报表高级查询参数
 */
@Data
@ApiModel(value = "客服推广公司费用报表高级查询参数")
public class CompanyCostQueryParams implements Serializable {

    @ApiModelProperty(value = "开始日期（必选）")
    private String beginTime;

    @ApiModelProperty(value = "结束日期（必选）")
    private String endTime;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private String deptIds;

    @ApiModelProperty(value = "推广公司ID")
    private Integer companyId;

    @ApiModelProperty(value = "推广公司名称")
    private String companyName;

    @ApiModelProperty(value = "获取方式值，见数字典GET_WAY:1-主动，2-被动")
    private int getWay;

    @ApiModelProperty(value = "费用类型：见数字典COST_TYPE:1-人民币，2-虚拟币")
    private int costType;

    @ApiModelProperty(value = "来源平台ID")
    private Integer sourceId;

    @ApiModelProperty(value = "来源平台名称")
    private String sourceName;
}
