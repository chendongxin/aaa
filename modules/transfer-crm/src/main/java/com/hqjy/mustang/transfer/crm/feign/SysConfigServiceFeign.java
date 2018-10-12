package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mustang-admin")
public interface SysConfigServiceFeign {

    @GetMapping(Constant.API_PATH + "/config/get/{code}")
    String getConfig(@PathVariable("code") String code);
}
