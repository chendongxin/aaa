package com.hqjy.mustang.common.model.crm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * transfer_source 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/12 15:47
 */
@Data
public class TransferSourceInfo implements Serializable {
    /**
     * 主键 source_id
     **/
    private Long sourceId;

    /**
     * 来源名称 name
     **/
    private String name;

    /**
     * 电子邮件域名 email_domain
     **/
    private String emailDomain;

    /**
     * 状态 0启用 1禁用 status
     **/
    private Integer status;

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