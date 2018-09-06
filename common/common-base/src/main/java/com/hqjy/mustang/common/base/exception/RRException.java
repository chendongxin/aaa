package com.hqjy.mustang.common.base.exception;


import com.hqjy.mustang.common.base.constant.StatusCode;

/**
 * 自定义异常
 *
 * @author : heshuangshuang
 * @date : 2018/3/23 13:25
 */
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RRException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.msg = statusCode.getMsg();
        this.code = statusCode.getCode();
    }

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
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
