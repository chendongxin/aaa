package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_dept 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/14 14:38
 */
@Mapper
public interface SysDeptDao extends BaseDao<SysDeptEntity, Long> {

    /**
     * 所有部门数据列表
     */
    List<SysDeptEntity> findAllDeptList();

    /**
     * 获取所有部门ID
     */
    List<Long> findAllDeptIdList();

    /**
     * 有效部门数据列表
     */
    List<SysDeptEntity> findValidDeptList();

    /**
     * 查询子部门
     */
    List<SysDeptEntity> findDeptByParentId(Long deptId);

    /**
     * 根据多个部门编号查询部门列表
     */
    List findListByDeptIds(Long[] deptIdList);

    /**
     * 根据部门id列表获取部门列表信息
     */
    List<SysDeptEntity> getListBydeptIdList(List<Long> deptIdList);

    /**
     * 根据部门id字符串获取部门信息
     *
     * @param deptIdList 部门ID集合字符串（'1','2','3'）
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:44:08
     */
    List<SysDeptEntity> getDeptEntityByDeptIds(@Param("deptIdList") String deptIdList);

    /**
     * 查询用户部门关系列表
     */
    List<SysDeptEntity> findDeptListByUserId(Long userId);

    /**
     * 根据部门名称获取部门Id
     *
     * @param deptName 部门名称
     * @return 返回部门ID
     * @author xyq 2018年10月12日09:12:52
     */
    Long getDeptByName(String deptName);
}