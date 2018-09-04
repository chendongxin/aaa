package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDeptEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysUserDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_user_dept 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/16 11:04
 */
@Mapper
public interface SysUserDeptDao extends BaseDao<SysUserDeptEntity, Long> {

    /**
     * 查询用户部门关系列表
     */
    List<SysUserDeptEntity> findDeptListByUserId(Long userId);

    /**
     * 查询用户部门ID列表
     */
    List<Long> findDeptIdByUserId(Long userId);

    /**
     * 删除用户部门关系
     */
    int deleteByUserId(Long userId);

    int deleteByUserIdeExclude(@Param("userId") Long userId, @Param("noUserDeptList") List<SysUserDeptEntity> noUserDeptList);

    /**
     * 根据用户ID，获取部门列表
     */
    List<SysDeptEntity> findDeptList(Long userId);

    /**
     * 查询用户部门列表,包含部门信息
     */
    List<SysUserDeptEntity> findUserDeptInfoList(Long userId);

    /**
     * 根绝角色编号查询用户编号列表
     */
    List<Long> findUserIdByDeptId(Long deptId);

    /**
     * 根据部门编号删除用户部门关系
     */
    int deleteByDeptId(Long deptId);
}