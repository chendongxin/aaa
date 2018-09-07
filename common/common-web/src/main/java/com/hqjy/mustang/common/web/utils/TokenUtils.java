package com.hqjy.mustang.common.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.exception.RRException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

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
     * @param userId 用户id
     * @param key    生成摘要的 密码
     */
    public static String createToken(Long userId, String username, String key) {
        return createToken(userId, username, key, expire);
    }

    public static String createToken(Long userId, String username, String key, Long expires) {
        try {
            return JWT.create()
                    // 签发时间
                    .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    // 过期时间
                    .withExpiresAt(Date.from(LocalDateTime.now().plus(expires, ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant()))
                    // jwt 的id
                    .withJWTId(UUID.randomUUID().toString())
                    // 用户id
                    .withClaim(Constant.JWT_TOKEN_USERID, userId)
                    // 用户名
                    .withClaim(Constant.JWT_TOKEN_USERNAME, username)
                    // 加密方式
                    .sign(Algorithm.HMAC256(key));
        } catch (UnsupportedEncodingException e) {
            throw new RRException("TOKEN创建失败：" + e.getMessage());
        }
    }

    /**
     * 验证token 有效性
     */
    public static void verify(String token, String password) throws UnsupportedEncodingException {
        JWT.require(Algorithm.HMAC256(password)).build().verify(token);
    }

    /**
     * 获得token 信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T tokenInfo(String token, String key, Class<T> clazz) {
        Claim claim = JWT.decode(token).getClaim(key);
        switch (clazz.getSimpleName()) {
            case "Boolean":
                return (T) claim.asBoolean();
            case "Integer":
                return (T) claim.asInt();
            case "Long":
                return (T) claim.asLong();
            default:
                return (T) claim.asString();
        }
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
