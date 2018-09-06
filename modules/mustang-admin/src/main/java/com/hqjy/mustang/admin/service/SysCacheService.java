package com.hqjy.mustang.admin.service;

/**
 * 缓存管理
 *
 * @author : heshuangshuang
 * @date : 2018/5/17 15:23
 */
public interface SysCacheService {

    /**
     * 清除所有授权缓存（权限，角色，部门）
     */
    void cleanAll();

    /**
     * 清除所有权限缓存
     */
    void cleanPerm();

    /**
     * 清除所有角色缓存
     */
    void cleanRole();

    /**
     * 清除所有部门缓存
     */
    void cleanDept();


    /**
     * 清除用户权限缓存
     */
    void cleanPerm(Long userId);

    /**
     * 清除用户角色缓存
     */
    void cleanRole(Long userId);

    /**
     * 清除用户部门缓存
     */
    void cleanDept(Long userId);

    /**
     * 清除用户所有授权缓存
     */
    void cleanUserAuz(Long userId);

    /**
     * 清除用户所有认证缓存
     */
    void cleanUserAut(Long userId);


}
