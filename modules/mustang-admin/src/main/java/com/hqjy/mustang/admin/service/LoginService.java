package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.dto.LoginUserDTO;

/**
 * 用户登录逻辑
 *
 * @author : heshuangshuang
 * @date : 2018/5/23 9:32
 */
public interface LoginService {

    /**
     * 根据用户和密码获取用户信息
     */
    LoginUserDTO getUserByPhone(String phone, String password);

    /**
     * 根据用户Id获取用户信息
     */
    LoginUserDTO getUserById(Long uesrId);

    /**
     * 执行登录逻辑
     */
    LoginUserDTO doLogin(LoginUserDTO userDTO);

    /**
     * 查询用户部门和角色
     */
    LoginUserDTO processDeptRole(LoginUserDTO userDTO);

}
