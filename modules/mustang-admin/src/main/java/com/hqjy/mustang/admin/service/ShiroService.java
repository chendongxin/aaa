package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.dto.LoginUserDTO;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 获取用户角色列表
     */
    Set<String> getUserRole(long userId);

    /**
     * 根据用户电话，查询用户
     *
     * @param phone 用户电话
     */
    LoginUserDTO findByPhone(String phone);

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户信息
     * @param ip        ip
     */
    int updateUserLoginInfo(LoginUserDTO loginUser, String ip);

    /**
     * 根据用户Id，查询用户
     *
     * @param userId 用户Id
     */
    LoginUserDTO findByUserId(Long userId);
}
