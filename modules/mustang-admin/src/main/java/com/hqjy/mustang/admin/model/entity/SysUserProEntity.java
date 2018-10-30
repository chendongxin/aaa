package com.hqjy.mustang.admin.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * sys_user_pro 实体类
 * 
 * @author : xyq
 * @date : 2018/10/29 10:36
 */
@Data
public class SysUserProEntity implements Serializable {
    /**
	 * 赛道id pro_id
	 **/
    private Long proId;

    /**
     * 赛道名称
     */
    private String name;

    /**
	 * 编号 id
	 **/
    private Integer id;

    /**
	 * 关联编号 user_id
	 **/
    private Long userId;

    /**
	 * 创建人编号 create_user_id
	 **/
    private Long createUserId;

    /**
	 * 创建时间 create_time
	 **/
    private Date createTime;

    /**
	 * 更新人编号 update_user_id
	 **/
    private Long updateUserId;

    /**
	 * 更新时间 update_time
	 **/
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 赛道Id列表
     */
    private List<Long> proIdList;
}