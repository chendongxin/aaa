package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysMenuEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity, Long> {

    /**
     * 保存角色菜单关系
     */
    int save(Long roleId, List<SysMenuEntity> menuList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> findMenuIdList(Long roleId);

    /**
     * 根据角色编号删除角色菜单关系
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据角色编号删除角色菜单关系
     */
    int deleteByRoleIds(Long[] roleIds);

    /**
     * 根据菜单编号删除角色菜单关系
     */
    int deleteByMenuIds(Long[] menuIds);
}
