package com.hqjy.mustang.admin.shiro;

import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 */
public class SysAuthcToken implements AuthenticationToken {

    private Long userId;
    private String userToken;
    private LoginUserDTO currentUser;

    public SysAuthcToken(Long userId, String userToken, LoginUserDTO currentUser) {
        this.userId = userId;
        this.userToken = userToken;
        this.currentUser = currentUser;
    }

    public LoginUserDTO getLoginUser() {
        return currentUser;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return userToken;
    }
}
