package com.hqjy.mustang.common.web.shiro;

import com.hqjy.mustang.common.web.model.UserDetails;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Set;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:07
 */
@Data
public class AuthToken extends UserDetails implements AuthenticationToken {

    public AuthToken(Long userId, String userName, String jwt) {
        super.setUserName(userName);
        super.setUserId(userId);
        super.setJwt(jwt);
    }

    public AuthToken(Long userId, String userName, String jwt, Set<Long> deptSet, Set<String> roleSet, Set<String> permSet) {
        super.setUserName(userName);
        super.setUserId(userId);
        super.setJwt(jwt);
        super.setDeptSet(deptSet);
        super.setRoleSet(roleSet);
        super.setPermSet(permSet);
    }

    @Override
    public Object getPrincipal() {
        return getUserName();
    }

    @Override
    public Object getCredentials() {
        return getJwt();
    }
}
