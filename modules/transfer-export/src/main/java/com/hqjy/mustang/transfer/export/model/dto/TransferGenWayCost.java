package com.hqjy.mustang.transfer.export.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xyq
 * @date create on 2018/10/24
 * @apiNote
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "招转推广方式费用对象")
public class TransferGenWayCost {


    @ApiModelProperty(value = "推广方式id")
    private Long wayId;

    @ApiModelProperty(value = "推广方式名称")
    private String genWay;

    @ApiModelProperty(value = "推广方式费用")
    private String cost = "0.0000";


    @ApiModelProperty(value = "创建日期", hidden = true)
    @JSONField(serialize = false)
    private String date;
}
