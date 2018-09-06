package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.allot.CustomerInfo;
import com.hqjy.mustang.transfer.export.feign.fallback.AllotServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/7/18 23:34
 */
@FeignClient(name = "transfer-crm-allot", fallback = AllotServiceFallbackImpl.class)
public interface AllotService {

    @GetMapping(Constant.API_PATH + "/reset")
    CustomerInfo reset();
}
