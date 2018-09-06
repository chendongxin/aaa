package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.entity.SysRoleEntity;
import com.hqjy.mustang.admin.model.entity.SysUserRoleEntity;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
public interface SysUserRoleService extends BaseService<SysUserRoleEntity, Long> {

    /**
     * 根绝角色编号查询用户编号列表
     */
    List<Long> getUserIdByRoleId(Long roleId);

    /**
     * 保存用户与角色关系
     */
    int save(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> getRoleIdList(Long userId);

    /**
     * 根据用户ID，获取角色列表
     */
    List<SysRoleEntity> getRoleList(Long userId);

    /**
     * 删除用户与角色关系
     */
    int deleteByUserId(Long userId);
}
