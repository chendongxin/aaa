package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.AuthService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
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

    private final AuthService authService;

    @Autowired
    public SysAuthApi(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 校验token，通过返回角色和权限信息
     */
    @GetMapping(value = "/check/{userId}/{jti}")
    public AuthCheckResult checkToken(@PathVariable("userId") Long userId, @PathVariable("jti") String jti) {
        return authService.checkToken(userId, jti);
    }

    /**
     * 查找用户编号对应的角色编码字符串
     */
    @PostMapping(value = "/getUserRoleSet/{userId}")
    public Set<String> getUserRoleSet(@PathVariable Long userId) {
        return authService.roleSet(userId);
    }

    /**
     * 查找用户编号对应的权限编码字符串
     */
    @PostMapping(value = "/getUserPermSet/{userId}")
    public Set<String> getUserPermSet(@PathVariable Long userId) {
        return authService.permSet(userId);
    }

}
