package com.hqjy.transfer.crm.modules.sys.service.impl;

import com.hqjy.transfer.common.redis.utils.RedisKeys.Dept;
import com.hqjy.transfer.common.redis.utils.RedisKeys.User;
import com.hqjy.transfer.common.redis.utils.RedisUtils;
import com.hqjy.transfer.crm.modules.sys.service.SysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓存管理，用于清空用户权限，角色，部门相关缓存
 *
 * @author : heshuangshuang
 * @date : 2018/5/17 15:23
 */
@Service
public class SysCacheServiceImpl implements SysCacheService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 清除所有授权缓存（权限，角色，部门）
     */
    @Override
    public void cleanAll() {
        redisUtils.cleanKey(User.auz("*"));
    }

    /**
     * 清除所有权限缓存
     */
    @Override
    public void cleanPerm() {
        redisUtils.cleanKey(User.perm("*"));
    }

    /**
     * 清除所有角色缓存
     */
    @Override
    public void cleanRole() {
        redisUtils.cleanKey(User.role("*"));
    }

    /**
     * 清除所有部门缓存
     */
    @Override
    public void cleanDept() {
        redisUtils.cleanKey(Dept.allDept("*"));
        redisUtils.cleanKey(User.curdept("*"));
        redisUtils.cleanKey(User.allDept("*"));
        redisUtils.cleanKey(User.subDept("*"));
    }

    /**
     * 清除用户权限缓存
     */
    @Override
    public void cleanPerm(Long userId) {
        redisUtils.delete(User.perm(userId));
    }

    /**
     * 清除用户角色缓存
     */
    @Override
    public void cleanRole(Long userId) {
        redisUtils.delete(User.role(userId));
        redisUtils.delete(User.allRole(userId));
    }

    /**
     * 清除用户部门缓存
     */
    @Override
    public void cleanDept(Long userId) {
        redisUtils.delete(User.curdept(userId));
        redisUtils.delete(User.allDept(userId));
        redisUtils.delete(User.subDept(userId));
    }

    /**
     * 清除用户所有授权缓存
     */
    @Override
    public void cleanUserAuz(Long userId) {
        cleanPerm(userId);
        cleanRole(userId);
        cleanDept(userId);
    }

    /**
     * 清除用户所有认证缓存
     */
    @Override
    public void cleanUserAut(Long userId) {
        redisUtils.delete(User.token(userId));
    }
}
