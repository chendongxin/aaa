package com.hqjy.mustang.websocket.utils;

import java.util.HashMap;

/**
 * 返回数据
 */
public class WsReturn extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL = 4;
    private static final String MSG = "msg";
    private static final String TYPE = "type";
    private static final String RESUTLT = "result";


    public WsReturn() {
        super();
    }

    public WsReturn(int initialCapacity) {
        super(initialCapacity);
    }

    public static WsReturn ok(int type, String msg) {
        //initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为 0.75，如果暂时无法确定初始值大小，请设置为 16
        WsReturn r = new WsReturn(INITIAL);
        r.put(TYPE, type);
        r.put(MSG, msg);
        return r;
    }

    public static WsReturn ok(int type, Object data) {
        //initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为 0.75，如果暂时无法确定初始值大小，请设置为 16
        WsReturn r = new WsReturn(INITIAL);
        r.put(TYPE, type);
        r.put(RESUTLT, data);
        return r;
    }

    public static WsReturn ok(int type, String msg, Object data) {
        WsReturn r = new WsReturn(INITIAL);
        r.put(TYPE, type);
        r.put(MSG, msg);
        r.put(RESUTLT, data);
        return r;
    }

    public static WsReturn ok(WsCode status) {
        return ok(status.getCode(), status.getMsg());
    }

    @Override
    public WsReturn put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public WsReturn add(String key, Object value) {
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