package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/1/19 15:30
 */
public interface SysUserService extends BaseService<SysUserEntity, Long> {
    /**
     * 查询用户基本信息
     */
    SysUserEntity findOneInfo(Long userId);

    /**
     * 修改密码
     */
    int updatePassword(HashMap<String, Object> param);

    /**
     * 修改个人信息
     */
    int updateUserInfo(SysUserEntity sysUser);

    /**
     * 修改头像
     */
    int updateUserAvatar(MultipartFile file);

    /**
     * 获取系统的所有用户
     *
     * @return 获取系统的所有用户
     */
    List<SysUserEntity> listAll();

    /**
     * 更新用户redis信息
     */
    void updateUserRedisInfo();

    /**
     * 用户修改部门
     */
    int updateUserDept(SysUserEntity user);


    /**
     * 根据部门ID，获取对应的所有用户
     *
     * @param deptId 部门ID
     * @return 返回部门下及其子部门下的所有用户
     */
    List<SysUserEntity> getUserByAllDeptId(Long deptId);

    /**
     * 根据部门ID，只获取当前部门节点的用户
     *
     * @param deptId 部门ID
     * @return 只获取当前部门节点的用户
     */
    List<SysUserEntity> getUserByDeptId(Long deptId);


    /**
     * 将用户集合转换成 用户id的字符串[12,13,14]->('12','13','14') add xyq 2018年7月23日17:37:41
     *
     * @param userEntityList 用户Id集合
     * @return 返回字符串
     */
    String userIdListToIdString(List<Long> userEntityList);
}
