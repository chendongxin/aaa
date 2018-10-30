package com.hqjy.mustang.quartz.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.quartz.feign.impl.SmsQuartzFeignIml;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description:
 * Autor: hutao
 * Date: 2018-10-30-15:08
 */
@FeignClient(name = "transfer-sms" , fallback = SmsQuartzFeignIml.class)
public interface SmsQuartzFeign {
    @GetMapping(value = Constant.API_PATH + "/status")
    R status();
}
