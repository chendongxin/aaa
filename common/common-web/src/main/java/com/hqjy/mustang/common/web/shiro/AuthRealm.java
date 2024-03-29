package com.hqjy.mustang.common.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/9/2 15:17
 */
public class AuthRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 将用户交给shiro去登录，将用户信息与线程绑定
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthToken authToken = (AuthToken) token;
        return new SimpleAuthenticationInfo(authToken, authToken.getJwt(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthToken userDetails = (AuthToken) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得角色信息
        authorizationInfo.addRoles(userDetails.getRoleSet());
        //获得授权信息
        authorizationInfo.addStringPermissions(userDetails.getPermSet());
        return authorizationInfo;
    }
}
