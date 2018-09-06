package com.hqjy.mustang.common.base.utils;

import com.hqjy.mustang.common.base.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author : heshuangshuang
 * @date : 2018/7/6 12:15
 */
@Slf4j
public class RSAUtil {
    /**
     * 生成秘钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 获取公钥(Base64编码)
     */
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * 获取私钥(Base64编码)
     */
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     *
     * @param pubStr
     * @return
     * @throws Exception
     */
    public static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     *
     * @param priStr
     * @return
     * @throws Exception
     */
    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     */
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    /**
     * 私钥解密
     */
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    /**
     * 字节数组转Base64编码
     */
    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    /**
     * Base64编码转字节数组
     */
    public static byte[] base642Byte(String base64Key) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(base64Key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 私钥解密
     */
    public static String privateDecrypt(String contentStr, String privateKeyStr) {
        try {
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            byte[] content = RSAUtil.base642Byte(contentStr);
            if (content != null) {
                return new String(RSAUtil.privateDecrypt(content, privateKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     */
    public static String publicEncrypt(String contentStr, String publicKeyStr) {
        try {
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            byte[] publicEncrypt = RSAUtil.publicEncrypt(contentStr.getBytes(), publicKey);
            return RSAUtil.byte2Base64(publicEncrypt);
            //加密后的内容Base64编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        /*try {
            //===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
            //生成RSA公钥和私钥，并Base64编码
            KeyPair keyPair = RSAUtil.getKeyPair();
            String publicKeyStr = RSAUtil.getPublicKey(keyPair);
            String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
            System.out.println("RSA公钥Base64编码:\n" + publicKeyStr);
            System.out.println();
            System.out.println("RSA私钥Base64编码:\n" + privateKeyStr);
            System.out.println();

            //=================客户端=================
            //hello, i am infi, good night!加密
            String message = "hello, i am infi, good night!";
            System.out.println();
            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密
            byte[] publicEncrypt = RSAUtil.publicEncrypt(message.getBytes(), publicKey);
            //加密后的内容Base64编码
            String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
            System.out.println("公钥加密并Base64编码的结果：\n" + byte2Base64);
            System.out.println();


            //##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################

VtiH1RM/G5OGgEVEg8X3mNqqDWwrZ7OF4/zJvCiTXRM5ndrkkxR4jcYJ8IXkhrgJpxMnwEUCd0mS
wTjpZ9w73z6BWOq4g/ia1MwMQMWbDVQOq0Pg2Oi+/84KeIedU7OKOQUVClcTq3I9r+F+Qt0zoTK/
pxTr6Ey13rYtRvmAxLw=
            //===================服务端================
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            //加密后的内容Base64解码
            byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
            //用私钥解密
            byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
            //解密后的明文
            System.out.println("解密后的明文:\n " + new String(privateDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            //加密后的内容Base64编码
            String byte2Base64 = RSAUtil.publicEncrypt("hq@#admin#@jy", Constant.RSA_PUBLIC_KEY);
            System.out.println(byte2Base64);
            System.out.println("解密后的明文:\n" + RSAUtil.privateDecrypt(byte2Base64, Constant.RSA_PRIVATE_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(RSAUtil.privateDecrypt(Constant.RSA_PRIVATE_KEY));
    }
}
