package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleUsableEntity;

import java.util.List;


/**
 * 角色部门关系业务，主要用于部门可使用的角色
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public interface SysRoleUsableService extends BaseService<SysRoleUsableEntity, Long> {

    /**
     * 查询role可使用角色
     */
    List<SysRoleUsableEntity> findByRoleId(Long roleId);

    /**
     * 可使用角色关系
     */
    int saveOrUpdate(Long roleId, List<Long> usableIdList);

    int deleteByRoleId(Long roleId);

    /**
     * 获取角色可使用的角色关系
     */
    List<SysRoleUsableEntity> getListByUsableId(List<Long> usableIdList);

    /**
     * 获取角色可使用的角色Id
     */
    List<Long> getIdListByUsableId(List<Long> usableIdList);
}
