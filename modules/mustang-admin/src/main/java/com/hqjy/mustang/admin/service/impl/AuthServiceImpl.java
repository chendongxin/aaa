package com.hqjy.mustang.admin.service.impl;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.admin.service.AuthService;
import com.hqjy.mustang.admin.service.ShiroService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author : heshuangshuang
 * @date : 2018/10/10 11:28
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public AuthCheckResult checkToken(Long userId, String jti) {
        AuthCheckResult authCheckResult = new AuthCheckResult();
        try {
            //读取redis中token信息，但是不改变原来的超时时间
            LoginUserDTO userDTO = redisUtils.get(RedisKeys.User.token(userId), LoginUserDTO.class, -1);
            if (null != userDTO) {
                if (jti.equals(userDTO.getJti())) {
                    //委托给Realm进行登录
                    //token续命
                    redisUtils.set(RedisKeys.User.token(userId), userDTO, TokenUtils.getProlong());
                    authCheckResult.setCheck(Constant.CheckToken.SUCCESS);
                    authCheckResult.setRoleSet(roleSet(userId));
                    authCheckResult.setPermSet(permSet(userId));
                    return authCheckResult;
                }
                authCheckResult.setCheck(Constant.CheckToken.TOKEN_OUT);
                return authCheckResult;
            }
            authCheckResult.setCheck(Constant.CheckToken.TOKEN_OVERDUE);
            return authCheckResult;
        } catch (Exception ignored) {

        }
        authCheckResult.setCheck(Constant.CheckToken.TOKEN_FAULT);
        return authCheckResult;
    }

    @Override
    public Set<String> permSet(Long userId) {
        //用户权限列表
        Set<String> permsSet;
        String permJson = redisUtils.get(RedisKeys.User.perm(userId));
        if (StringUtils.isNotEmpty(permJson)) {
            permsSet = JsonUtil.fromJson(permJson, new TypeToken<Set<String>>() {
            }.getType());
        } else {
            permsSet = shiroService.getUserPermissions(userId);
            //权限缓存600秒
            redisUtils.set(RedisKeys.User.perm(userId), permsSet, 600);
        }
        return permsSet;
    }

    @Override
    public Set<String> roleSet(Long userId) {
        // 用户角色列表
        Set<String> roleSet;
        String roleJson = redisUtils.get(RedisKeys.User.role(userId));
        if (StringUtils.isNotEmpty(roleJson)) {
            roleSet = JsonUtil.fromJson(roleJson, new TypeToken<Set<String>>() {
            }.getType());
        } else {
            roleSet = shiroService.getUserRole(userId);
            //权限缓存600秒
            redisUtils.set(RedisKeys.User.role(userId), roleSet, 600);
        }
        return roleSet;
    }
}
