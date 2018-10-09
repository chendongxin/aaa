package com.hqjy.mustang.allot.feign;

import com.hqjy.mustang.allot.feign.impl.SysUserApiServiceFallbackImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-admin", fallback = SysUserApiServiceFallbackImpl.class)
public interface SysUserApiService {

    /**
     * 根据用户Id查询用户
     */
    @GetMapping(value = Constant.API_PATH + "/user/{userId}")
    SysUserInfo findOne(@PathVariable("userId") Long userId);

}
