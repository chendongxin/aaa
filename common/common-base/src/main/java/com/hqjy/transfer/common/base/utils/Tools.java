package com.hqjy.transfer.common.base.utils;

import jodd.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : heshuangshuang
 * @date : 2018/5/28 15:06
 */
public class Tools {
    /**
     * BASE64编码
     *
     * @param str String
     */
    public static String base64Encode(String str) {
        return Base64.encodeToString(str);
    }

    /**
     * BASE64编码
     *
     * @param arr byte[]
     */
    public static String base64Encode(byte[] arr) {
        return Base64.encodeToString(arr);
    }

    /**
     * BASE64解码
     */
    public static String base64Decoder(String str) {
        return Base64.decodeToString(str);
    }

    /**
     * sha1
     */
    public static String sha1Hex(String str) {
        return DigestUtils.sha1Hex(str);
    }

    /**
     * md5
     */
    public static String md5Hex(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha1Hex(String str, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (salt != null) {
                digest.reset();
                digest.update(salt.getBytes());
            }
            byte[] hashed = digest.digest(str.getBytes());
            hashed = digest.digest(hashed);
            return base64Encode(hashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取异常的详细情况String(包括异常类和位置等debug信息，可以存放到数据库)
     */
    public static String exceptionInfo(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    public static void main(String[] args) {
      /*  System.out.println(sha1Hex("asdfasdfasdssssssssssssssssdfasfds"));
        System.out.println(System.currentTimeMillis());*/
        long time = System.currentTimeMillis();
        System.out.println(time);
        //System.out.println(new Sha256Hash(String.valueOf(time), "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8").toBase64());
        System.out.println(sha1Hex("password"));
        System.out.println(sha1Hex(String.valueOf(time), "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8"));
    }

}
