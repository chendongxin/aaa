package com.hqjy.mustang.admin.shiro;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.admin.service.ShiroService;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.common.web.model.UserDetails;
import com.hqjy.mustang.common.web.shiro.AuthToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author : heshuangshuang
 * @date : 2018/1/19 15:31
 */
@Component
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDetails user = (UserDetails) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();
        //用户权限列表
        Set<String> permsSet;
        String permJson = redisUtils.get(RedisKeys.User.perm(userId));
        if (StringUtils.isNotEmpty(permJson)) {
            permsSet = JsonUtil.fromJson(permJson, new TypeToken<Set<String>>() {
            }.getType());
        } else {
            permsSet = shiroService.getUserPermissions(userId);
            //权限缓存3600秒
            redisUtils.set(RedisKeys.User.perm(userId), permsSet, 3600);
        }

        // 用户角色列表
        Set<String> roleSet;
        String roleJson = redisUtils.get(RedisKeys.User.role(userId));
        if (StringUtils.isNotEmpty(roleJson)) {
            roleSet = JsonUtil.fromJson(roleJson, new TypeToken<Set<String>>() {
            }.getType());
        } else {
            roleSet = shiroService.getUserRole(userId);
            //权限缓存3600秒
            redisUtils.set(RedisKeys.User.role(userId), roleSet, 3600);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        info.setRoles(roleSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthToken authToken = (AuthToken) token;
        // 使用缓存中的userToken和当前验证token进行对比
        return new SimpleAuthenticationInfo(authToken, authToken.getJwt(), getName());
    }
}
