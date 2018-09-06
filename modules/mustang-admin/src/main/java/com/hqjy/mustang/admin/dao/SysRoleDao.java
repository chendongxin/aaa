package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_role 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:52
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity, Long> {

    /**
     * 获取所有角色列表
     */
    List<SysRoleEntity> findAllRoleList();

    /**
     * 角色下拉数据
     */
    List<SysRoleEntity> findDrop(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 根据角色编号查询角色信息列表
     */
    List<SysRoleEntity> findRoleListByRoleIds(Long[] roleIds);

    /**
     * 根据角色编号查询角色信息列表
     */
    List<SysRoleEntity> findRoleListByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

}