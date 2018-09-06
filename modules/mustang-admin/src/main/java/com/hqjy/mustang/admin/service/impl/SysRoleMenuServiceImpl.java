package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.admin.dao.SysRoleMenuDao;
import com.hqjy.mustang.admin.model.entity.SysMenuEntity;
import com.hqjy.mustang.admin.model.entity.SysRoleMenuEntity;
import com.hqjy.mustang.admin.service.SysRoleMenuService;
import com.hqjy.mustang.admin.utils.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity, Long> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(Long roleId, List<SysMenuEntity> menuList) {
        //先删除角色与菜单关系
        int count = baseDao.deleteByRoleId(roleId);
        if (menuList.size() == 0) {
            return count;
        }
        //保存角色与菜单关系
        SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
        sysRoleMenuEntity.setRoleId(roleId);
        sysRoleMenuEntity.setMenuList(menuList);
        sysRoleMenuEntity.setCreateId(ShiroUtils.getUserId());

        return baseDao.save(sysRoleMenuEntity);
    }

    /**
     * 根据角色ID，获取菜单ID列表
     */
    @Override
    public List<Long> findMenuIdList(Long roleId) {
        return baseDao.findMenuIdList(roleId);
    }

    /**
     * 根据角色编号删除角色菜单关系
     */
    @Override
    public int deleteByRoleId(Long roleId) {
        return baseDao.deleteByRoleId(roleId);
    }

    /**
     * 删除角色菜单关系
     */
    @Override
    public int deleteByRoleIds(Long[] roleIds) {
        return baseDao.deleteByRoleIds(roleIds);
    }

    /**
     * 根据菜单编号删除角色菜单关系
     */
    @Override
    public int deleteByMenuIds(Long[] menuIds) {
        return baseDao.deleteByMenuIds(menuIds);
    }

}
