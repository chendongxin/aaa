package com.hqjy.mustang.allot.feign;

import com.hqjy.mustang.allot.feign.impl.SysConfigApiServiceFallbackImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 获取系统配置信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-admin", fallback = SysConfigApiServiceFallbackImpl.class)
public interface SysConfigApiService {

    /**
     * 根据配置编码获取系统配置信息
     */
    @GetMapping(value = Constant.API_PATH + "/config/info/{code}")
    String getConfig(@PathVariable("code") String code);

}
