package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * sys_user 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:40
 */
@Component
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity, Long> {

    /**
     * 根据部门ID集合，获取对应的所有用户
     *
     * @param deptIdList 部门ID集合
     * @return 返回部门下的所有用户(一个用户可能有多个部门, 去重相同用户)
     */
    List<SysUserEntity> findUserByDeptIdList(@Param("deptIdList") List<Long> deptIdList);

    /**
     * 根据部门ID，获取对应的所有用户
     *
     * @param deptId 部门ID
     * @return 返回部门下的所有用户
     */
    List<SysUserEntity> getUserByDeptId(Long deptId);


    /**
     * 根据角色编号获取用户
     *
     * @param roleCode 角色编号
     * @return 返回用户集合
     * @author xyq 2018年10月19日17:05:29
     */
    List<SysUserEntity> getUserListByRoleCode(String roleCode);
}