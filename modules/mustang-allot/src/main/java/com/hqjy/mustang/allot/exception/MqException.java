package com.hqjy.mustang.allot.exception;

import com.hqjy.mustang.allot.constant.AllotCode;

/**
 * 消息队列处理自定义异常
 *
 * @author : heshuangshuang
 * @date : 2018/3/23 13:25
 */
public class MqException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public MqException(AllotCode allotCode) {
        super(allotCode.getMsg());
        this.msg = allotCode.getMsg();
        this.code = allotCode.getCode();
    }

    public MqException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MqException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MqException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MqException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
