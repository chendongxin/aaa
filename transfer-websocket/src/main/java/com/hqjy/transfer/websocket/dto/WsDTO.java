package com.hqjy.transfer.websocket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * websocket操作对象封装
 *
 * @author : heshuangshuang
 * @date : 2017/12/2 12:55
 */
@Data
public class WsDTO implements Serializable {
    /**
     * 操作消息的类型，0：主动断开指定用户 1：发送消息
     */
    private int operation;
    /**
     * 消息类型， 0：通知，1：消息，2：代办
     */
    private int type;
    /**
     * 消息的接收者，如果为0，发送给所有人
     */
    private String source;
    /**
     * 消息的发送者, 如果为0，则为系统发送
     */
    private String destination;
    /**
     * 业务消息体
     */
    private Object data;
}
