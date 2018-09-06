package com.hqjy.mustang.admin.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 生成token
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
@ConfigurationProperties(prefix = "jwt-token")
@Component
public class TokenUtils {

    /**
     * token有效时长
     */
    private static long expire;
    /**
     * 续命时长
     */
    private static long prolong;

    /**
     * 创建jwt token
     *
     * @param userId   用户id
     * @param password 生成摘要的用户密码
     */
    public static String create(Long userId, String password) {
        String token = "";
        try {
            token = JWT.create().
                    withExpiresAt(new Date(System.currentTimeMillis() + (expire * 1000))).
                    withSubject(String.valueOf(userId)).
                    sign(Algorithm.HMAC256(password));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 验证token 有效性
     */
    public static void verify(String token, String password) throws UnsupportedEncodingException {
        JWT.require(Algorithm.HMAC256(password)).build().verify(token);
    }

    /**
     * 获得token 主体
     */
    public static Long getSub(String token) {
        DecodedJWT dJWT = JWT.decode(token);
        return Long.valueOf(dJWT.getSubject());
    }

    /**
     * 获得企业账户
     */
    public static String getIssuer(String token) {
        DecodedJWT dJWT = JWT.decode(token);
        return dJWT.getIssuer();
    }

    /**
     * 获得token 信息
     */
    public static String getInfoAsString(String token, String key) {
        DecodedJWT dJWT = JWT.decode(token);
        return dJWT.getClaim(key).asString();
    }

    /**
     * 获得token 信息
     */
    public static Long getInfoAsLong(String token, String key) {
        DecodedJWT dJWT = JWT.decode(token);
        return dJWT.getClaim(key).asLong();
    }


    public static long getExpire() {
        return expire;
    }

    public static void setExpire(long expire) {
        TokenUtils.expire = expire;
    }

    public static long getProlong() {
        return prolong;
    }

    public static void setProlong(long prolong) {
        TokenUtils.prolong = prolong;
    }
}
