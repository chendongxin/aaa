package com.hqjy.mustang.quartz.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.quartz.feign.impl.TransferCallApiServiceFallbackImpl;
import com.hqjy.mustang.quartz.feign.impl.TransferResumeApiServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:16
 */
@FeignClient(name = "transfer-call", fallback = TransferCallApiServiceFallbackImpl.class)
public interface TrasferCallApiService {

    /**
     * 获取通话记录
     */
    @PostMapping(value = Constant.API_PATH + "/syncRecord/{timestamp}")
    R callOut(@RequestParam(value = "timestamp", required = true) Long timestamp);

}
