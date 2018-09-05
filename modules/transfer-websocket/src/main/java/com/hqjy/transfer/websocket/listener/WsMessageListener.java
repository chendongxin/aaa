package com.hqjy.transfer.websocket.listener;

import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.websocket.dto.WsDTO;
import com.hqjy.transfer.websocket.service.WsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2017/12/3 22:20
 */
@Component
@Slf4j
public class WsMessageListener implements MessageListener {
    @Autowired
    private WsService wsService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String jsonMessage = message.toString();
        if (!StringUtils.isEmpty(jsonMessage)) {
            // 处理业务消息
            wsService.process(JsonUtil.fromJson(jsonMessage, WsDTO.class));
        }
    }
}
