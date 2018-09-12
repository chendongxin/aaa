package com.hqjy.mustang.common.base.utils;

import com.aliyun.oss.OSSClient;
import com.hqjy.mustang.common.base.exception.RRException;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里上传文件工具类
 *
 * @author xyq 2018年9月12日18:10:22
 */
public class OssFileUtils {

    private static OSSClient client;
    /**
     * 阿里访问accessKeyId
     */
    private static final String ALI_YUN_ACCESS_KEY_ID = "LTAIAO3rjtbLdTNb";
    /**
     * 阿里访问密钥 accessKeySecret
     */
    private static final String ALI_YUN_ACCESS_KEY_SECRET = "HZv4RFvj2Aro2dhqk4EO4opMreWu2m";
    /**
     * 阿里云buckName
     */
    private static final String ALI_YUN_BUCKET_NAME = "hqcrm";
    /**
     * 阿里云endPoint
     */
    private static final String ALI_YUN_ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";


    /**
     * 默认的超时时间 1小时
     */
    private final static int DEFAULT_EXPIRE = 60 * 60 * 1000;

    static {
        client = new OSSClient(ALI_YUN_ENDPOINT, ALI_YUN_ACCESS_KEY_ID, ALI_YUN_ACCESS_KEY_SECRET);
    }

    /**
     * 上传文件
     *
     * @param data     字节文件
     * @param dir      上传目录
     * @param fileName 文件名称
     * @return 返回path
     */
    public static String uploadFile(byte[] data, String dir, String fileName) {
        String path = dir + "/" + fileName;
        try {
            client.putObject(ALI_YUN_BUCKET_NAME, path, new ByteArrayInputStream(data));
        } catch (Exception e) {
            throw new RRException("上传文件失败，请检查配置信息", e);
        }
        return path;
    }

    /**
     * 获取访问的url对象 by -liuwenlong
     *
     * @param objectKey     阿里云 object key ,Object key 的组成是 目录+文件名的形式
     * @param timeoutPeriod 超时时间，单位是秒
     * @return url
     */
    public static URL getVisitUrl(String objectKey, int timeoutPeriod) {
        if (StringUtils.isBlank(objectKey)) {
            return null;
        }
        if (timeoutPeriod == 0) {
            timeoutPeriod = DEFAULT_EXPIRE;
        }
        Date date = new Date(System.currentTimeMillis() + timeoutPeriod * 1000);

        return client.generatePresignedUrl(ALI_YUN_BUCKET_NAME, objectKey, date);
    }

}
