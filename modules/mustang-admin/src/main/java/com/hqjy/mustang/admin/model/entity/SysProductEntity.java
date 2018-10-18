package com.hqjy.mustang.admin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_product 实体类
 * 
 * @author : xyq
 * @date : 2018/09/18 20:03
 */
@Data
public class SysProductEntity implements Serializable {
    /**
	 * 编号 pro_id
	 **/
    private Long proId;

    /**
	 * 名称 name
	 **/
    private String name;

    /**
	 * 状态 0 启用 1 禁用 status
	 **/
    private Byte status;

    /**
     * 注释 memo
     **/
    private String memo;

    /**
	 * 创建人编号 create_user_id
	 **/
    private Long createUserId;

    /**
     * 创建人名称
     */
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
     * 更新人名称
     */
    private String updateUserName;

    /**
	 * 更新时间 update_time
	 **/
    private Date updateTime;

    /**
     * 是否被用 0 未被用 1 被用
     **/
    private Integer sign;

    private static final long serialVersionUID = 1L;
}