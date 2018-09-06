package com.hqjy.mustang.websocket.constant;

import io.netty.util.AttributeKey;

/**
 * @author : heshuangshuang
 * @date : 2017/12/2 12:50
 */
public class WsConstant {
    /**
     * 主动断开连接
     */
    public static final int SOCKET_TYPE_DISCONNECT = 0;
    /**
     * 消息推送
     */
    public static final int SOCKET_TYPE_SEND = 1;
    /**
     * websocket连接的url websocket
     */
    public static final String WEBSOCKET_PATH = "/websocket";
    /**
     * 通道处理器之间存放token参数的key
     */
    public static final AttributeKey<String> CHANNEL_USERID_KEY = AttributeKey.valueOf("netty.websocket.userId");

}
