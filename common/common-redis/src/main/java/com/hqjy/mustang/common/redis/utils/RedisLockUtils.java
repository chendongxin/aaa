package com.hqjy.mustang.common.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * 分布式锁工具
 *
 * @author : heshuangshuang
 * @date : 2018/6/4 14:29
 */
@Component
public class RedisLockUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 上锁,直接返回上锁结果，不等待上锁成功
     * setNX and expiration
     *
     * @param key        key
     * @param value      value
     * @param expiration 超时时间
     * @return 获取锁是否成功
     */
    public boolean setLock(String key, String value, Long expiration) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            // setNX 并设置超时时间 ms
            redisConnection.set(key.getBytes(), value.getBytes(), Expiration.milliseconds(expiration), RedisStringCommands.SetOption.SET_IF_ABSENT);
            // 如果设置以后value是自己唯一标识的，说明设置锁是成功的
            return Arrays.equals(value.getBytes(), redisConnection.get(key.getBytes()));
        });
    }

    /**
     * 上锁,一直等待上锁成功
     *
     * @param key
     * @param value
     * @param expiration redis超时时间
     * @param retry      获取锁的重试时间
     * @return
     */
    public boolean setLock(String key, String value, long expiration, long retry) {
        try {
            while (true) {
                boolean flag = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                    // setNX 并设置超时时间 ms
                    redisConnection.set(key.getBytes(), value.getBytes(), Expiration.milliseconds(expiration), RedisStringCommands.SetOption.SET_IF_ABSENT);
                    // 如果设置以后value是自己唯一标识的，说明设置锁是成功的
                    return Arrays.equals(value.getBytes(), redisConnection.get(key.getBytes()));
                });
                if (flag) {
                    return true;
                }
                // 等待重试
                Thread.sleep(retry);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 解锁
     *
     * @param key   key
     * @param value value
     * @return 解锁是否成功
     */
    public Boolean unLock(String key, String value) {
        return redisTemplate.execute(new UnLockScript(), Collections.singletonList(key), value).equals(1L);
    }
}
