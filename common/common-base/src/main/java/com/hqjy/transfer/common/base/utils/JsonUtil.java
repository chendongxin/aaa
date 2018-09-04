package com.hqjy.transfer.common.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * JSONG工具类
 *
 * @author : heshuangshuang
 * @date : 2018/5/22 17:05
 */
public class JsonUtil {

    public final static Gson GSON = new Gson();

    private JsonUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * gson序列化
     */
    public static String toJson(Object entity) {
        return GSON.toJson(entity);
    }

    /**
     * Gson Token反序列化
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> T parseObject(String json, Type typeOfT) {
        return JSONObject.parseObject(json, typeOfT);
    }

    /**
     * 对象转Map
     */
    public static <T> T toMap(Object entity, Type typeOfT) {
        return JsonUtil.fromJson(toJson(entity), typeOfT);
    }

    /**
     * fastjson序列化
     */
    public static String toJSONString(Object entity) {
        return JSON.toJSONString(entity);
    }

    /**
     * fastjson反序列化
     */
    public static <T> T toObject(String json, Class<T> c) {
        return JSON.parseObject(json, c);
    }

    /**
     * fastjson转map
     */
    public static Map toMap(String json) {
        return (Map) JSON.parse(json);
    }


}
