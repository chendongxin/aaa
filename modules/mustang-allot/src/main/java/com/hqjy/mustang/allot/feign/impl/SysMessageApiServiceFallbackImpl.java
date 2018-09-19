package com.hqjy.mustang.allot.feign.impl;

import com.hqjy.mustang.allot.feign.SysMessageApiService;
import com.hqjy.mustang.common.model.crm.MessageSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class SysMessageApiServiceFallbackImpl implements SysMessageApiService {

    /**
     * 发送通知
     */
    @Override
    public void sendNotice(MessageSendVO msg) {

    }

    /**
     * 发送消息
     */
    @Override
    public void sendMessage(MessageSendVO msg) {

    }

    /**
     * 主动断开本地连接
     */
    @Override
    public void disconnect(Long userId) {

    }
}
