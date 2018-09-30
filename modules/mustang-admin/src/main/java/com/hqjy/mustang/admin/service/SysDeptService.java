package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public interface SysDeptService extends BaseService<SysDeptEntity, Long> {

    /**
     * 获取所有部门列表
     */
    List<SysDeptEntity> getAllDeptList();

    /**
     * 获取所有部门ID
     */
    List<Long> getAllDeptIdList();

    /**
     * 部门管理树数据
     */
    HashMap<String, List<SysDeptEntity>> getRecursionTree(boolean showRoot);

    /**
     * 用户部门树
     */
    Map<String, List<SysDeptEntity>> getUserDeptTree(Boolean isRoot, Boolean showRoot);

    /**
     * 部门选择数据,排除状态为不为正常的管理
     */
    HashMap<String, List<SysDeptEntity>> getSelectTree(boolean showRoot);

    /**
     * 检测是否越权，增加和修改的部门是否在当前用户的部门之下
     */
    boolean checkPermission(boolean userAllDept, Long deptId);

    /**
     * 根据部门id列表获取部门列表信息
     */
    List<SysDeptEntity> getListBydeptIdList(List<Long> deptIdList);


    /**
     * ADD xyq 2018年7月13日11:17:22
     * 获取所选部门的旗下部门（包括所选部门）
     *
     * @param deptId 部门ID
     * @return 返回部门集合
     */
    List<Long> getAllDeptUnderDeptId(Long deptId);

    /**
     * 获取所有有效非禁用的部门数据 add xyq 2018年7月23日16:56:47
     *
     * @return 返回部门集合
     */
    List<SysDeptEntity> findValidDeptList();

    /**
     * 将List<Long>部门ID集合转换成字符串 如：[1,2,3]->（'1','2','3'）  add xyq 2018年7月23日16:56:47
     *
     * @param deptIdList 部门ID集合
     * @return 返回结果
     */
    String deptIdListToString(List<Long> deptIdList);

    /**
     * 获取用户所在部门及子部门
     */
    List<SysDeptEntity> getUserDeptList(Long userId);
}
