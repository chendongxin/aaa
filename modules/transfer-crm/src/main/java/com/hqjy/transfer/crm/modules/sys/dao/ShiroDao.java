package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.crm.modules.sys.model.dto.LoginUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * shiro操作的 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@Mapper
public interface ShiroDao {


    /**
     * 根据电话号码查询用户
     *
     * @param phone 电话号码
     */
    LoginUserDTO findByPhone(String phone);

    /**
     * 管理员查询所有权限
     */
    List<String> findAllPerms();

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> findAllPermsByUserId(Long userId);

    /**
     * 查询用户的所有角色
     *
     * @param userId 用户ID
     */
    List<String> findAllRoleByUserId(Long userId);

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户信息
     */
    int updateUserLoginInfo(LoginUserDTO loginUser);

    /**
     * 根据用户Id，查询用户
     */
    LoginUserDTO findByUserId(Long userId);
}