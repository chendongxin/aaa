package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sys_role_menu 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:55
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity, Long> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> findMenuIdList(Long roleId);

    /**
     * 删除角色拥有的菜单
     */
    int deleteByRoleId(Long roleId);

    /**
     * 删除角色菜单关系
     */
    int deleteByRoleIds(Long[] roleIds);

    /**
     * 根据菜单编号删除角色菜单关系
     */
    int deleteByMenuIds(Long[] menuIds);
}