package com.hqjy.transfer.websocket.service.impl;

import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.websocket.config.WebSocketServer;
import com.hqjy.transfer.websocket.constant.WsConstant;
import com.hqjy.transfer.websocket.dto.WsDTO;
import com.hqjy.transfer.websocket.service.WsService;
import com.hqjy.transfer.websocket.utils.WsCode;
import com.hqjy.transfer.websocket.utils.WsReturn;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2017/12/10 20:37
 */
@Service
@Slf4j
public class WsServiceImpl implements WsService {

    /**
     * 处理业务消息
     */
    @Override
    public void process(WsDTO ws) {
        // 消息处理类型
        int optione = ws.getOperation();
        // 消息类型
        int type = ws.getType();
        // 消息主体
        String data = JsonUtil.toJson(WsReturn.ok(type, ws.getData()));
        // 消息发送者
        String souces = ws.getSource();
        // 消息接受者
        String dest = ws.getDestination();
        // 原生消息
        String message = JsonUtil.toJson(ws);
        switch (optione) {
            // 断开指定用户
            case WsConstant.SOCKET_TYPE_DISCONNECT:
                log.debug("断开连接请求,用户{}：", dest);
                disconnect(dest);
                break;
            //发送给本地指定用户
            case WsConstant.SOCKET_TYPE_SEND:
                log.debug("推送消息请求，用户：{},内容：{}", dest, data);
                sendMessage(dest, data);
                break;
            default:
                log.debug("无法识别的处理类型：{}", message);
        }
    }

    @Override
    public void disconnect(String userId) {
        Channel channel = WebSocketServer.channelPool.get(userId);
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.toJson(R.ok(WsCode.WS_DISCONNECT))));
            channel.close();
            log.debug("主动断开用户：{} 成功", userId);
        }
    }

    @Override
    public void sendMessage(String userId, String message) {

        // 群发消息
        if ("0".equals(userId)) {
            log.debug("群发消息,{}", message);
            WebSocketServer.CHANNEL_GROUP.writeAndFlush(new TextWebSocketFrame(message));
            return;
        }

        // 指定发送消息
        Channel channel = WebSocketServer.channelPool.get(userId);
        if (channel != null) {
            log.debug("用户：{} 推送消息成功", userId);
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }

    }

    @Override
    public void processMessage(String userId, String message) {
        try {
            WsDTO wsDTO = JsonUtil.fromJson(message, WsDTO.class);
            wsDTO.setSource(userId);
            sendMessage(wsDTO.getDestination(), JsonUtil.toJson(wsDTO));
            log.debug("用户:{} 给：{} 发送消息：{}", userId, wsDTO.getDestination(), message);
        } catch (Exception e) {
            log.error("用户发送消息失败:{}:{}:{}", userId, message, e.getMessage());
        }
    }
}
