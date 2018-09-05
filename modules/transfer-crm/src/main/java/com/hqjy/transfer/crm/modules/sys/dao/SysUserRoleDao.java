package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sys_user_role 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/23 11:53
 */
@Mapper
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity, Long> {

    /**
     * 根绝角色编号查询用户编号列表
     */
    List<Long> findUserIdByRoleId(Long roleId);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> findRoleIdList(Long userId);

    /**
     * 根据用户ID，获取角色列表
     */
    List<SysRoleEntity> findRoleList(Long userId);

    /**
     * 删除用户角色关系
     */
    int deleteByUserId(Long userId);
}