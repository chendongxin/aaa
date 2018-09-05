package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;

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
}