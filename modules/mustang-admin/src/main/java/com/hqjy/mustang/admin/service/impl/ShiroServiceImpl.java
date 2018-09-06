package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.admin.dao.ShiroDao;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.admin.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Shrio授权认证管理
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private ShiroDao shiroDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if (userId == SystemId.SUPER_ADMIN) {
            permsList = shiroDao.findAllPerms();
        } else {
            permsList = shiroDao.findAllPermsByUserId(userId);
        }
        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        // Shiro要求权限字符串不能为null或空
        permsList.stream().filter(StringUtils::isNotEmpty).
                forEach(perms -> permsSet.addAll(Arrays.asList(perms.trim().split(","))));
        return permsSet;
    }

    /**
     * 获取用户角色列表
     */
    @Override
    public Set<String> getUserRole(long userId) {
        List<String> roleList = shiroDao.findAllRoleByUserId(userId);
        // Shiro要求角色列字符串不能为null或空
        return roleList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    /**
     * 根据用户电话，查询用户
     *
     * @param phone 用户电话
     */
    @Override
    public LoginUserDTO findByPhone(String phone) {
        return shiroDao.findByPhone(phone);
    }

    /**
     * 更新用户登录信息
     *
     * @param loginUser 登录用户信息
     * @param ip        ip
     */
    @Override
    public int updateUserLoginInfo(LoginUserDTO loginUser, String ip) {
        LoginUserDTO sysUser = new LoginUserDTO();
        sysUser.setUserId(loginUser.getUserId());
        sysUser.setLoginIp(ip);
        sysUser.setLoginTime(new Date());
        return shiroDao.updateUserLoginInfo(sysUser);
    }

    /**
     * 根据用户Id，查询用户
     *
     * @param userId 用户Id
     */
    @Override
    public LoginUserDTO findByUserId(Long userId) {
        return shiroDao.findByUserId(userId);
    }
}
