package com.hqjy.mustang.transfer.export.model.query;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gmm
 * @date create on 2018年10月11日15:25:23
 * @apiNote 客户报表查询参数
 */
@Data
@ApiModel(value = "客户报表查询参数")
public class InvalidQueryParams {

    /**
     * 用户部门集合字符创
     */
    @JSONField(serialize = false)
    private String userAllDeptId;

    @ApiModelProperty(value = "归属人id")
    private Long userId;

    @ApiModelProperty(value = "归属人名称")
    private Long userName;

    @ApiModelProperty(value = "开始创建时间")
    private String beginTransferCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private String endTransferCreateTime;

    @ApiModelProperty(value = "开始无效时间")
    private String beginCreateTime;

    @ApiModelProperty(value = "结束无效时间")
    private String endCreateTime;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "手机")
    private String phone;

}
