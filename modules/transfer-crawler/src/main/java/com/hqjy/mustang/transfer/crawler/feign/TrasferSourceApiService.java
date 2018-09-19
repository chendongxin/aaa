package com.hqjy.mustang.transfer.crawler.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.TransferSourceInfo;
import com.hqjy.mustang.transfer.crawler.feign.impl.TrasferSourceServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 登陆逻辑查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "trasfer-crm", fallback = TrasferSourceServiceFallbackImpl.class)
public interface TrasferSourceApiService {

    /**
     * 根据邮箱域名查询来源配置
     */
    @GetMapping(value = Constant.API_PATH + "/source/findByEmailDomain")
    TransferSourceInfo findByEmailDomain(@RequestParam("email") String email);
}
