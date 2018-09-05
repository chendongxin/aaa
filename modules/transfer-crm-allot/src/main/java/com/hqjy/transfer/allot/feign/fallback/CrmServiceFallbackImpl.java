package com.hqjy.transfer.allot.feign.fallback;

import com.hqjy.transfer.allot.feign.CrmService;
import com.hqjy.transfer.common.model.crm.MessageSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/9/5 20:09
 */
@Service
@Slf4j
public class CrmServiceFallbackImpl implements CrmService {

    /**
     * 发送通知
     */
    @Override
    public void sendNotice(MessageSendVO msg) {
        log.error("调用{}异常:{}", "发送通知", "msg");
    }

    /**
     * 发送消息
     */
    @Override
    public void sendMessage(MessageSendVO msg) {
        log.error("调用{}异常:{}", "发送消息", "msg");
    }

    /**
     * 主动断开本地连接
     */
    @Override
    public void disconnect(Long userId) {
        log.error("调用{}异常:{}", "主动断开本地连接", "userId");
    }
}
