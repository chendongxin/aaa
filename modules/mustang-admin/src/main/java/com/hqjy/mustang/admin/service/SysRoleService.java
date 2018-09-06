package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.entity.SysRoleEntity;

import java.util.List;


/**
 * 角色
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public interface SysRoleService extends BaseService<SysRoleEntity, Long> {

    /**
     * 获取所有角色列表
     */
    List<SysRoleEntity> getAllRoleList();

    /**
     * 角色下拉列表
     */
    List<SysRoleEntity> getDrop(boolean showAll);
}
