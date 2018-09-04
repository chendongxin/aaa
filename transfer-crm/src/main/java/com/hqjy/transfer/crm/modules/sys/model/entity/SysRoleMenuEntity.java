package com.hqjy.transfer.crm.modules.sys.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * sys_role_menu 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:55
 */
@Data
public class SysRoleMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     **/
    private Long id;

    /**
     * 角色编号 role_id
     **/
    private Long roleId;

    /**
     * 菜单编号 menu_id
     **/
    private Long menuId;
    /**
     * 菜单类型 menu_type
     **/
    private Integer menuType;

    /**
     * 创建人编号 create_id
     **/
    private Long createId;

    /**
     * 创建日期 create_time
     **/
    private Date createTime;

    /**
     * 菜单，批量
     */
    private List<SysMenuEntity> menuList;
}