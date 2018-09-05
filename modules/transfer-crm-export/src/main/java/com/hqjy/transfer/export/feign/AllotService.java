package com.hqjy.transfer.export.feign;

import com.hqjy.transfer.common.base.constant.Constant;
import com.hqjy.transfer.common.model.allot.CustomerInfo;
import com.hqjy.transfer.export.feign.fallback.AllotServiceFallbackImpl;
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
