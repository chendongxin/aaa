package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserDeptEntity;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
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

    /**
     * 根据部门ID集合字符串获取用户和部门信息
     *
     * @param deptIds 部门Id
     * @return 返回
     * @author xyq 2018年10月19日11:55:51
     */
    List<UserDeptInfo> getUserDeptInfo(@Param("deptIds") String deptIds);


    /**
     * 根据部门ID集合字符串和角色编号获取用户和部门信息
     *
     * @param deptIds  部门Id
     * @param roleCode 角色编号
     * @return 返回
     * @author xyq 2018年10月31日16:14:26
     */
    List<UserDeptInfo> getUserDeptByRoleCode(@Param("deptIds") String deptIds, @Param("roleCode") String roleCode);


    /**
     * 获取用户所属部门
     */
    SysDeptEntity getUserDept(Long userId);
}