package com.hqjy.mustang.common.redis.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * redis解锁脚本
 *
 * @author : heshuangshuang
 * @date : 2018/6/4 11:10
 */
public class UnLockScript implements RedisScript<Long> {
    private final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * @return The SHA1 of the script, used for executing Redis evalsha command
     */
    @Override
    public String getSha1() {
        return DigestUtils.sha1Hex(SCRIPT);
    }

    /**
     * @return The script result type. Should be one of Long, Boolean, List, or deserialized value type. Can be null if
     * the script returns a throw-away status (i.e "OK")
     */
    @Override
    public Class<Long> getResultType() {
        return Long.class;
    }

    /**
     * @return The script contents
     */
    @Override
    public String getScriptAsString() {
        return SCRIPT;
    }
}
