package com.hqjy.transfer.allot.feign;

import com.hqjy.transfer.allot.feign.fallback.CrmServiceFallbackImpl;
import com.hqjy.transfer.common.base.constant.Constant;
import com.hqjy.transfer.common.model.crm.MessageSendVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/7/18 23:34
 */
@FeignClient(name = "transfer-crm", fallback = CrmServiceFallbackImpl.class)
public interface CrmService {

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
    @DeleteMapping(Constant.API_PATH + "/message")
    void disconnect(@PathVariable("userId") Long userId);
}
