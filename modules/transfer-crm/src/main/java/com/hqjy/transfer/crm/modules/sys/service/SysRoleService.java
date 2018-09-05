package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleEntity;

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
