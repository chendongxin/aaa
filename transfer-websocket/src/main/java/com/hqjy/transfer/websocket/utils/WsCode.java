package com.hqjy.transfer.websocket.utils;

/**
 * 业务状态码
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/5 18:25
 */
public enum WsCode {
    WS_UNAUTHORIZED(1000, "token验证失败"),
    WS_DISCONNECT(1001, "主动断开"),
    WS_CONNECTION(1002, "连接成功"),
    WS_HEARTBEAT(1003, "heartbeat");

    private final int code;
    private final String msg;

    private WsCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}