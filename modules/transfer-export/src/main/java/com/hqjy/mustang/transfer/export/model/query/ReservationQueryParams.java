package com.hqjy.mustang.transfer.export.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyq
 * @date create on 2018年10月10日14:21:23
 * @apiNote 邀约报表查询参数（与预约客户列表查询条件一样）
 */
@Data
@ApiModel(value = "邀约报表查询参数")
public class ReservationQueryParams {

    /**
     * 用户部门集合字符创
     */
    @JSONField(serialize = false)
    private String userAllDeptId;

    /**
     * 用户ID
     */
    @JSONField(serialize = false)
    private String userId;

    @ApiModelProperty(value = "开始创建时间")
    private String beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private String endCreateTime;

    @ApiModelProperty(value = "开始预约时间")
    private String beginAppointTime;

    @ApiModelProperty(value = "结束预约时间")
    private String endAppointTime;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "上门状态")
    private String visitStatus;

}
