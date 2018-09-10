package com.hqjy.mustang.transfer.crm.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * transfer_source 推广来源平台实体类
 *
 *
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Data
public class TransferSourceEntity implements Serializable {
    /**
     * 主键 source_id
     **/
    private Long sourceId;

    /**
     * 来源名称 name
     **/
    private String name;

    /**
     * 状态 0启用 1禁用 status
     **/
    private Byte status;

    /**
     * 创建人编号 create_user_id
     **/
    private Long createUserId;

    /**
     * 创建人名称 create_user_name
     **/
    private String createUserName;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 更新人编号 update_user_id
     **/
    private Long updateUserId;

    /**
     * 更新人名称 update_user_name
     **/
    private String updateUserName;

    /**
     * 更新时间 update_time
     **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}