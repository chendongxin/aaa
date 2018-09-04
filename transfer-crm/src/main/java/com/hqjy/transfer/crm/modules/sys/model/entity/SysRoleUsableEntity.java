package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_role_usable 实体类
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/07/16 17:23
 */
@Data
public class SysRoleUsableEntity implements Serializable {
    private Long id;

    /**
	 * 角色ID role_id
	 **/
    private Long roleId;

    /**
	 * 可使用角色ID usable_id
	 **/
    private Long usableId;

    /**
	 * 创建人编号 create_id
	 **/
    private Long createId;

    /**
	 * 创建时间 create_time
	 **/
    private Date createTime;

    private static final long serialVersionUID = 1L;
}