package com.hqjy.mustang.transfer.sms.fegin;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.transfer.sms.fegin.fallback.SysUserExtendServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 登陆逻辑查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-admin", fallback = SysUserExtendServiceFallbackImpl.class)
public interface SysUserExtendApiService {

    /**
     * 根据用户Id查询 用户扩展信息
     */
    @GetMapping(value = Constant.API_PATH + "/userExtend/findByUserId/{userId}")
    SysUserExtendInfo findByUserId(@PathVariable("userId") Long userId);
}
