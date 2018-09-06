package com.hqjy.mustang.common.base.utils;

import com.hqjy.mustang.common.base.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : heshuangshuang
 * @date : 2018/7/2 17:37
 */
@Slf4j
public class RestHttpUtils implements AutoCloseable {

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(1800000)
            .setConnectTimeout(1800000)
            .setConnectionRequestTimeout(1800000)
            .build();

    private static RestHttpUtils instance = null;

    private RestHttpUtils() {
    }

    /**
     * 获取实例
     */
    public static RestHttpUtils getInstance() {
        if (instance == null) {
            instance = new RestHttpUtils();
        }
        return instance;
    }

    /**
     * 执行请求，返回HttpEntity
     */
    private HttpEntity getEntity(HttpRequestBase request) {
        try {
            request.setConfig(requestConfig);
            // 执行请求
            Optional<CloseableHttpResponse> rep = Optional.ofNullable(HttpClients.createDefault().execute(request));
            return rep.map(HttpResponse::getEntity).orElse(null);
        } catch (IOException e) {
            log.error("请求网络资源发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * GET
     */
    public String sendGetByHeader(String url, Map<String, String> headers) {
        // 创建get请求
        log.info("sendGetByHeader:" + url);
        HttpGet httpGet = new HttpGet(url);
        //设置 setHeader
        headers.forEach(httpGet::setHeader);
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpGet));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }

    /**
     * GET
     */
    public String sendGetByHeader(String url, Map<String, String> headers, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        params.forEach((key, value) -> {
            sb.append("&").append(key).append("=").append(String.valueOf(value));
        });
        sb.replace(0, 1, url + (url.contains("?") ? "" : "?"));
        // 创建get请求
        HttpGet httpGet = new HttpGet(sb.toString());
        //设置 setHeader
        headers.forEach(httpGet::setHeader);
        log.info("sendGetByHeader:" + url);
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpGet));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }

    /**
     * get请求获取响应体
     */
    public CloseableHttpResponse sendPost(String url) {
        log.info("sendGet:" + url);
        HttpRequestBase httpRequest = new HttpPost(url);
        try {
            httpRequest.setConfig(requestConfig);
            // 执行请求
            return HttpClients.createDefault().execute(httpRequest);
        } catch (IOException e) {
            log.error("请求网络资源发生异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * REST url参数
     */
    public String send(String method, String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        params.forEach((key, value) -> {
            sb.append("&").append(key).append("=").append(String.valueOf(value));
        });
        sb.replace(0, 1, url + (url.contains("?") ? "&" : "?"));
        String paramUrl = sb.toString();
        return send(method, paramUrl);
    }

    /**
     * REST url参数
     */
    public String send(String method, String url) {
        HttpRequestBase httpRequest;
        switch (method.toUpperCase()) {
            case "GET":
                httpRequest = new HttpGet(url);
                break;
            case "POST":
                httpRequest = new HttpPost(url);
                break;
            case "PUT":
                httpRequest = new HttpPut(url);
                break;
            case "DELETE":
                httpRequest = new HttpDelete(url);
                break;
            default:
                throw new RRException(getClass() + "未指定请求方法");
        }
        log.debug(method.toUpperCase() + "-url:" + url);
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpRequest));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }


    /**
     * POST
     */
    public String sendPostByHeader(String url, Map<String, String> headers, Serializable params) {
        // 创建POST请求
        log.info("sendPostByHeader:" + url);
        HttpPost httpPost = new HttpPost(url);
        //设置 setHeader
        headers.forEach(httpPost::setHeader);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 创建参数队列
        if (params != null) {
            httpPost.setEntity(objectToJson(params));
        }
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpPost));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }

    public String sendPostByHeader(String url, Map<String, String> headers, Map<String, Object> urlParams, Serializable params) {
        // 创建POST请求
        log.info("sendPostByHeader:" + url);
        StringBuilder sb = new StringBuilder();
        urlParams.forEach((key, value) -> {
            sb.append("&").append(key).append("=").append(String.valueOf(value));
        });
        sb.replace(0, 1, url + (url.contains("?") ? "" : "?"));
        HttpPost httpPost = new HttpPost(sb.toString());
        //设置 setHeader
        headers.forEach(httpPost::setHeader);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 创建参数队列
        if (params != null) {
            httpPost.setEntity(objectToJson(params));
        }
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpPost));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }

    private StringEntity objectToJson(Serializable params) {
        if (params != null) {
            // 这里使用fastjson进行序列化
            return new StringEntity(JsonUtil.toJSONString(params), "UTF-8");
        }
        return null;
    }

    /**
     * PUT
     */
    public String sendPutByHeader(String url, Map<String, String> headers, Serializable params) {
        // 创建get请求
        log.info("sendPutByHeader:" + url);
        HttpPut httpPut = new HttpPut(url);
        //设置 setHeader
        headers.forEach(httpPut::setHeader);
        httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 创建参数队列
        if (params != null) {
            httpPut.setEntity(objectToJson(params));
        }
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpPut));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }

    /**
     * PUT
     */
    public String sendDeleteByHeader(String url, Map<String, String> headers) {
        // 创建get请求
        log.info("sendDeleteByHeader:" + url);
        HttpDelete httpDelete = new HttpDelete(url);
        //设置 setHeader
        headers.forEach(httpDelete::setHeader);
        Optional<HttpEntity> httpEntity = Optional.ofNullable(getEntity(httpDelete));
        return httpEntity.map(httpEntity1 -> entityToClass(httpEntity1, String.class)).orElse(null);
    }


    /**
     * HttpEntity 转 指定类型
     */
    @SuppressWarnings("unchecked")
    private static <T> T entityToClass(HttpEntity httpEntity, Class<T> clazz) {
        try {
            switch (clazz.getSimpleName()) {
                case "String":
                    return (T) EntityUtils.toString(httpEntity, "UTF-8");
                case "byte[]":
                    return (T) EntityUtils.toByteArray(httpEntity);
                default:
                    String result = EntityUtils.toString(httpEntity, "UTF-8");
                    if (StringUtils.isNotEmpty(result)) {
                        return JsonUtil.fromJson(result, clazz);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建请求体
     */
    private UrlEncodedFormEntity buildEntity(List<NameValuePair> list) {
        try {
            return new UrlEncodedFormEntity(list, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
