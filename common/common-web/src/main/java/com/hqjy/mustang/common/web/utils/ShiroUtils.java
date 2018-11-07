package com.hqjy.mustang.common.web.utils;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.web.model.UserDetails;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

import java.util.Optional;
import java.util.Set;

/**
 * Shiro工具类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 */
public class ShiroUtils {
    /**
     * 生成用户密码
     */
    public static String userPassword(String userPwd, String salt) {
        String algorithmName = "md5";
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName, userPwd, salt, hashIterations);
        return hash.toHex();
    }

    /**
     * 获取用户Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户对象
     */
    public static UserDetails getUser() {
        return (UserDetails) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 判断是否电销专员
     */
    public static Boolean isGeneralSeat() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.SALE_COMMISSIONER.getCode());
    }

    /**
     * 判断是否客服专员
     */
    public static Boolean isServiceCommissioner() {
        return SecurityUtils.getSubject().hasRole(Constant.Role.SERVICE_COMMISSIONER.getCode());
    }

    /**
     * 判断是否超级管理员
     */
    public static Boolean isSuperAdmin() {
        SecurityUtils.getSubject().hasRole(Constant.Role.SUPER_ADMIN.getCode());
        return  SecurityUtils.getSubject().hasRole(Constant.Role.SUPER_ADMIN.getCode());
    }

    /**
     * 判断是否管理员
     */
    public static Boolean isAdmin() {

        return SecurityUtils.getSubject().hasRole(Constant.Role.ADMIN.getCode());
    }

    /**
     * 获得用户名
     */
    public static String getUserName() {
        return Optional.ofNullable(getUser()).map(UserDetails::getUserName).orElse("游客");
    }

    /**
     * 获得用户id
     */
    public static Long getUserId() {
        return Optional.ofNullable(getUser()).map(UserDetails::getUserId).orElse(0L);
    }

    /**
     * 获得用户部门id
     */
    public static Set<Long> getDeptIdSet() {
        return Optional.ofNullable(getUser()).map(UserDetails::getDeptSet).orElse(null);
    }

    /**
     * 判断用户是否登录
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 用户注销
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}
