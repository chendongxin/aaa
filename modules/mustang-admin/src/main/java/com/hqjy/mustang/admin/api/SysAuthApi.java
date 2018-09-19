package com.hqjy.mustang.admin.api;

import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.admin.model.dto.LoginUserDTO;
import com.hqjy.mustang.admin.service.ShiroService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 10:17
 */
@RestController
@RequestMapping(Constant.API_PATH + "/auth")
@Slf4j
public class SysAuthApi {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 校验token，通过返回角色和权限信息
     */
    @GetMapping(value = "/check/{userId}/{jti}")
    public AuthCheckResult checkToken(@PathVariable("userId") Long userId, @PathVariable("jti") String jti) {
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

    /**
     * 查找用户编号对应的角色编码字符串
     */
    @PostMapping(value = "/getUserRoleSet/{userId}")
    public Set<String> getUserRoleSet(@PathVariable Long userId) {
        return roleSet(userId);
    }

    /**
     * 查找用户编号对应的权限编码字符串
     */
    @PostMapping(value = "/getUserPermSet/{userId}")
    public Set<String> getUserPermSet(@PathVariable Long userId) {
        return permSet(userId);
    }


    private Set<String> permSet(Long userId) {
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

    private Set<String> roleSet(Long userId) {
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
