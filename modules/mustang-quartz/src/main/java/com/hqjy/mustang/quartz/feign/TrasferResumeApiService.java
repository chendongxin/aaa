package com.hqjy.mustang.quartz.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.quartz.feign.impl.TransferResumeApiServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:16
 */
@FeignClient(name = "transfer-crawler", fallback = TransferResumeApiServiceFallbackImpl.class)
public interface TrasferResumeApiService {

    /**
     * 抓取简历，从数据库中最后一条的时间至现在的
     */
    @GetMapping(value = Constant.API_PATH + "/crawler/start")
    boolean start();

    /**
     * 抓取简历，参数指定多少小时之前的
     */
    @GetMapping(value = Constant.API_PATH + "/crawler/start")
    boolean start(@RequestParam(value = "hour", required = false) Integer hour);

}
