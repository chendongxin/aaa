package com.hqjy.mustang.websocket.service;

import com.hqjy.mustang.websocket.dto.WsDTO;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2017/12/10 20:46
 */
public interface WsService {

    /**
     * 处理业务消息
     */
    void process(WsDTO wsDTO);

    /**
     * 主动断开本地连接
     *
     * @param userId 用户id
     */
    void disconnect(String userId);

    /**
     * 发送消息，只在本地服务进行发送
     *
     * @param userId  用户id
     * @param message 发送的消息
     */
    void sendMessage(String userId, String message);

    /**
     * 处理用户发送的消息
     *
     * @param userId  用户id
     * @param message 发送的消息
     */
    void processMessage(String userId, String message);
}
