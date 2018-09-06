package com.hqjy.mustang.common.redis.utils;

import java.io.Serializable;

/**
 * Redis所有Keys
 *
 * @author : heshuangshuang
 * @date : 2018/1/19 14:15
 */
public class RedisKeys {

    /**
     * 公司key
     */
    public static class Prefix {
        /**
         * 不同公司生成不同key前缀
         */
        private static String buildKey(String type, Object key) {
            return "mustang:" + type + ":" + key;
        }
    }

    /**
     * 配置key
     */
    public static class Config {
        public static String key(String key) {
            return Prefix.buildKey("sys:config", key);
        }
    }

    /**
     * 天润key add by xyq on 2018年6月15日17:31:33
     */
    public static class TiNet {
        /**
         * 坐席cno key
         */
        public final static String CNO = "cno";

        public static String key(String key) {
            return Prefix.buildKey("tiTet:info", key);
        }
    }

    /**
     * 客户key add by xyq on 2018年7月6日11:36:25
     */
    public static class Business {

        /**
         * 分布式redis锁-商机回收key
         */
        public final static String RECYCLE_LOCK_KEY = "recycleLockKey";

        /**
         * 分布式redis锁-商机领取key
         */
        public final static String RECEIVE_LOCK_KEY = "receiveLockKey";

        /**
         * 省份集合key
         */
        public final static String PROVINCE_KEY = "provinceList";

        public static String province(String key) {
            return Prefix.buildKey("biz:province", key);
        }

        public static String school(String key) {
            return Prefix.buildKey("biz:school", key);
        }

        public static String recycleLock(String key) {
            return Prefix.buildKey("biz:recycleLock", key);
        }

        public static String receiveLock(String key) {
            return Prefix.buildKey("biz:receiveLock", key);
        }


    }

    /**
     * 部门key
     */
    public static class Dept {
        /**
         * 获取部门下（含自身）所有部门列表
         */
        public static String allDept(Serializable deptId) {
            return Prefix.buildKey("sys:deptId:info", deptId);
        }
    }

    /**
     * 用户key
     */
    public static class User {
        /**
         * 用户认证前缀
         */
        public static String aut(String key) {
            return "sys:user:aut:" + key;
        }

        /**
         * 用户授权前缀
         */
        public static String auz(String key) {
            return "sys:user:auz:" + key;
        }

        /**
         * 获取用户token
         */
        public static String token(Long userId) {
            return Prefix.buildKey(aut("token"), userId);
        }

        /**
         * 获取用户权限列表
         */
        public static String perm(Serializable userId) {
            return Prefix.buildKey(auz("perm"), userId);
        }

        /**
         * 获取用户直接部门列表
         */
        public static String curdept(Serializable userId) {
            return Prefix.buildKey(auz("dept:cur"), userId);
        }

        /**
         * 获取用户所有部门列表
         */
        public static String allDept(Serializable userId) {
            return Prefix.buildKey(auz("dept:all"), userId);
        }

        /**
         * 获取用户所有子部门列表
         */
        public static String subDept(Serializable userId) {
            return Prefix.buildKey(auz("dept:sub"), userId);
        }

        /**
         * 获取用户角色列表
         */
        public static String role(Serializable userId) {
            return Prefix.buildKey(auz("role"), userId);
        }

        /**
         * 获取用户所有角色列表
         */
        public static String allRole(Long userId) {
            return Prefix.buildKey(auz("role:all"), userId);
        }
    }

    /**
     * 分配Key
     */
    public static class Allot {
        /**
         * 延时消费,获取唯一锁ID,用于获取和删除分布式锁
         */
        public static String phoneLook(String phone) {
            return Prefix.buildKey("allot:phone:lock", phone);
        }

        /**
         * 延时消费,获取唯一锁ID,用于获取和删除分布式锁
         */
        public static String delayLook(String msgId) {
            return Prefix.buildKey("allot:delay:lock", msgId);
        }

        /**
         * 延时消费key
         */
        public static String delayKey(String msgId) {
            return Prefix.buildKey("allot:delay:key", msgId);
        }

        /**
         * 延时消费message
         */
        public static String delayMessage(String msgId) {
            return Prefix.buildKey("allot:delay:message", msgId);
        }

        public static String key(String key) {
            return Prefix.buildKey("allot", key);
        }

        public static String expire(Object deptId) {
            return Prefix.buildKey("allot:expire", deptId);
        }

        /**
         * 获取唯一锁ID,用于获取和删除分布式锁
         */
        public final static String LOCK_VERSION = "lockVersion";
        /**
         * 能够进行分配算法的锁
         */
        public final static String PROCESS_LOCK = "processLock";
        /**
         * 排序队列
         */
        public final static String SORT_LIST = "sortList";
        /**
         * 已分配完成队列
         */
        public final static String FINISH_SET = "finishSet";
        /**
         * 分配当前权重
         */
        public final static String CURR_WEIGTH = "currWeigth";

    }

    /**
     * 请求nc的异步任务
     */
    public static class Nc {
        /**
         * 待处理队列
         */
        public final static String SAVE = "nc:sync:save:list";
        /**
         * 正在处理队列
         */
        public final static String SAVE_PROCESS = "nc:sync:save:process";

        /**
         * NC更新待处理队列
         */
        public final static String UPDATE = "nc:sync:update:list";

        /**
         * NC更新正在处理队列
         */
        public final static String UPDATE_PROCESS = "nc:sync:update:process";

        /**
         * NC保存跟进记录待处理队列
         */
        public final static String SAVE_FOLLOW = "nc:sync:save_follow:list";
        /**
         * NC保存跟进记录正在处理队列
         */
        public final static String SAVE_FOLLOW_PROCESS = "nc:sync:save_follow:process";
    }

}
