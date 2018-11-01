package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.model.entity.SysUserDeptEntity;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;

import java.util.List;

/**
 * 用户部门管理
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public interface SysUserDeptService extends BaseService<SysUserDeptEntity, Long> {

    /**
     * 查询用户部门列表
     */
    List<SysUserDeptEntity> getUserDeptList(Long userId);

    /**
     * 保存用户部门关系
     */
    int save(Long userId, List<SysUserDeptEntity> userDeptList);

    int save(Long userId, List<SysUserDeptEntity> userDeptList, List<SysUserDeptEntity> noUserDeptList);

    /**
     * 查询用户部门Id列表
     */
    List<Long> getUserDeptId(Long userId);

    /**
     * 获取用户所有的部门，包含子部门
     */
    List<Long> getUserAllDeptId(Long userId);

    /**
     * 获取用户所有的子部门,不包含用户所在部门递归查询处理
     */
    List<Long> getUserSubDeptId(Long userId);

    /**
     * new  by xieyuqing 2018年6月13日15:02:52
     *
     * @param userId 用户id
     * @return 返回用户所有部门id集合
     */
    List<Long> getUserDeptIdList(Long userId);

    /**
     * 获取用户所属部门
     */
    SysDeptEntity getUserDept(Long userId);

    /**
     * 递归获取用户部门和子部门
     *
     * @param isRoot       是否显示根节点
     * @param list         需要遍历的列表
     * @param parentIdList 父节点编号列表
     */
    List<Long> recursionDept(boolean isRoot, List<SysDeptEntity> list, List<Long> parentIdList);

    /**
     * 针对招转预约功能部门选择提供的接口：当前用户所负责的部门校区（包含子部门）
     *
     * @return 结果
     * @author xyq
     * @date 2018年10月29日10:14:27
     */
    List<SysDeptEntity> getUserDeptSchool();

    /**
     * 删除用户部门关系
     */
    int deleteByUserId(Long userId);

    /**
     * 根据用户ID，获取部门列表
     */
    List<SysDeptEntity> getDeptList(Long userId);

    /**
     * 查询用户部门列表,包含部门信息
     */
    List<SysUserDeptEntity> getUserDeptInfoList(Long userId);

    /**
     * 根绝部门编号查询用户编号列表
     */
    List<Long> getUserIdByDeptId(Long deptId);

    /**
     * 删除人员与部门关系
     */
    int deleteByDeptId(Long deptId);


    /**
     * 根据部门名称集合字符串获取用户和部门信息
     *
     * @param deptName 部门名称
     * @return 返回
     */
    List<UserDeptInfo> getUserDeptInfo(String deptName);

    /**
     * 根据部门Id获取部门下节点所有用户（电销专员）和部门信息
     *
     * @param deptId 部门Id
     * @return 返回
     */
    List<UserDeptInfo> getUserDeptInfoByDeptId(Long deptId);

    /**
     * 根据部门ID集合字符串和角色编号获取用户和部门信息
     *
     * @return 返回
     * @author xyq 2018年10月31日16:14:26
     */
    List<UserDeptInfo> getUserDeptByRoleCode();
}
