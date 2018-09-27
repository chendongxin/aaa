package com.hqjy.mustang.allot.feign;

import com.hqjy.mustang.allot.feign.impl.SysMessageApiServiceFallbackImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息发送服务
 *
 * @author : heshuangshuang
 * @date : 2018/9/5 19:50
 */
@FeignClient(name = "mustang-admin", fallback = SysMessageApiServiceFallbackImpl.class)
public interface SysMessageApiService {
    /**
     * 发送通知
     */
    @PostMapping(Constant.API_PATH + "/message/sendNotice")
    void sendNotice(@RequestBody MessageSendVO msg);

    /**
     * 发送消息
     */
    @PostMapping(Constant.API_PATH + "/message/sendMessage")
    void sendMessage(@RequestBody MessageSendVO msg);

    /**
     * 主动断开本地连接
     */
    @DeleteMapping(Constant.API_PATH + "/message/{userId}")
    void disconnect(@PathVariable("userId") Long userId);

}
