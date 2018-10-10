package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 登陆逻辑查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-admin", fallback = AuthApiServiceFallbackImpl.class)
public interface AuthApiService {

    /**
     * 通过用户名查询用户、角色信息
     */
    @GetMapping(value = Constant.API_PATH + "/auth/check/{userId}/{jti}")
    AuthCheckResult checkToken(@PathVariable("userId") Long userId, @PathVariable("jti") String jti);
}
