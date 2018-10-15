package com.hqjy.mustang.common.base.utils;


import com.hqjy.mustang.common.base.constant.StatusCode;

import java.util.HashMap;


/**
 * 返回数据
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final int SUCCESS = StatusCode.SUCCESS.getCode();
    private static final int ERROR = StatusCode.FAILURE.getCode();
    private static final String SUCCESS_MSG = StatusCode.SUCCESS.getMsg();
    private static final String ERROR_MSG = StatusCode.FAILURE.getMsg();
    private static final int INITIAL = 4;
    private static final String MSG = "msg";
    private static final String CODE = "code";
    private static final String RESUTLT = "result";


    public R() {
        super();
    }

    public R(int initialCapacity) {
        super(initialCapacity);
    }

    public static R ok(int code, String msg) {
        //initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为 0.75，如果暂时无法确定初始值大小，请设置为 16
        R r = new R(INITIAL);
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    public static R ok() {
        return ok(SUCCESS, SUCCESS_MSG);
    }

    public static R ok(int code) {
        return ok().put(CODE, code);
    }

    public static R ok(String msg) {
        return ok().put(MSG, msg);
    }


    public static R ok(Object data) {
        return ok().put(RESUTLT, data);
    }

    public static R result(Object data) {
        return ok().put(RESUTLT, data);
    }

    public static R ok(String msg, Object data) {
        return ok(msg).put(RESUTLT, data);
    }

    public static R ok(StatusCode status) {
        return ok().put(MSG, status.getMsg()).put(CODE, status.getCode());
    }

    public static R error(int code, String msg) {
        R r = new R(INITIAL);
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    public static R error() {
        return error(ERROR, ERROR_MSG);
    }

    public static R error(String msg) {
        return error(ERROR, msg);
    }

    public static R error(Object data) {
        return error().put(RESUTLT, data);
    }

    public static R error(String msg, Object data) {
        return error(msg).put(RESUTLT, data);
    }

    public static R error(StatusCode status) {
        return ok().put(MSG, status.getMsg()).put(CODE, status.getCode());
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public R add(String key, Object value) {
        Object object = super.getOrDefault(RESUTLT, new HashMap<>());
        try {
            HashMap<String, Object> result = (HashMap<String, Object>) object;
            result.put(key, value);
            super.put(RESUTLT, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
