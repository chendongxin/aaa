package com.hqjy.transfer.allot.service;

import com.alibaba.fastjson.JSONObject;
import com.hqjy.transfer.allot.model.dto.WeigthRoundDTO;
import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.redis.utils.RedisKeys;
import com.hqjy.transfer.common.redis.utils.RedisKeys.Allot;
import com.hqjy.transfer.common.redis.utils.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 权重轮询算法实现
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:42
 */
@Slf4j
public abstract class AbstractWeightRound {
    @Autowired
    protected RedisLockUtils redisLockUtils;
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;
    @Autowired
    protected ValueOperations<String, String> valueOperations;
    @Autowired
    protected ListOperations<String, Object> listOperations;
    @Autowired
    protected SetOperations<String, Object> setOperations;
    @Autowired
    protected HashOperations<String, String, Object> hashOperations;

    /**
     * 锁的最大生命 60秒
     */
    protected static Long LOCK_EXPIRE = 1000 * 60L;
    /**
     * 未获取到分配算分最大等待时间 2秒
     */
    protected static Long WAIT_EXPIRE = 1000 * 2L;


    /**
     * key前缀
     */
    abstract public String keyPrefix();

    /**
     * 初始化数据的来源，数据库查询
     */
    abstract public List<Object> initData(Long id);

    public WeigthRoundDTO getOne(Long id) {
        Long processLockId = getLockVersion(keyPrefix(), id);
        // 没拿到锁，就2秒后重新去获取，直到拿到锁为止
        boolean flag = redisLockUtils.setLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId), LOCK_EXPIRE, WAIT_EXPIRE);
        // 获得分配锁
        if (flag) {
            // 如果没有版本号,或则没有排序队列,数据初始化检测
            if (listOperations.size(buildKey(keyPrefix(), Allot.SORT_LIST, id)) == 0) {
                log.info("没有排序队列,进行数据初始化操作");
                this.resetData(id);
            }

            // list长度为0,没有排序人员，不进行算法
            if (listOperations.size(buildKey(keyPrefix(), Allot.SORT_LIST, id)) == 0) {
                log.info("排序列表的长度长度为0");
                // 资源清理删除此id的所有的算法数据，这里可能会存在线程安全问题，因为初始化锁和进行算法的锁不是同一个；
                Set<String> keys = redisTemplate.keys(buildKey(keyPrefix(), "*", id));
                keys.forEach(s -> redisTemplate.delete(s));
                // 释放锁
                redisLockUtils.unLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId));
                return null;
            }

            // 队列右出对获得一个人员，并将它进行左入队，给此人分配，查询此人是否有分配的权利, 阻塞式，如果队列为0，则会等待，直到超时
            WeigthRoundDTO user = JsonUtil.fromJson(String.valueOf(listOperations.rightPopAndLeftPush(buildKey(keyPrefix(), Allot.SORT_LIST, id), buildKey(keyPrefix(), Allot.SORT_LIST, id), LOCK_EXPIRE, TimeUnit.MILLISECONDS)), WeigthRoundDTO.class);

            // 获取当前人员的固定权重
            Integer weigth = user.getWeights();
            // 原子加当前人员的分配权重
            Long newWgt = hashOperations.increment(buildKey(keyPrefix(), Allot.CURR_WEIGTH, id), String.valueOf(user.getId()), 1L);

            if (newWgt <= weigth) {
                log.info("新权重小于固定权重，分配成功");
                //当前人员分配一个以后比固定权重小或者相等，说明分配正常，直接返回此人员

                // 设置排序队列失效时间，用于重置算法，但是不完全靠谱，因为可能会被其他队列进行重置，导致永远达不到失效时间
              /*  String exprice = valueOperations.get(RedisKeys.Allot.expire());
                if (exprice != null) {
                    redisTemplate.keys(RedisKeys.Allot.key("*") + ":*:" + RedisKeys.Allot.SORT_LIST).forEach(s -> {
                        DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        //字符串转换成LocalDate类型
                        Date expriceDate = Date.from(LocalDateTime.parse(exprice, ymd).atZone(ZoneId.systemDefault()).toInstant());
                        redisTemplate.expireAt(s, expriceDate);
                    });
                }*/
                // 释放锁
                redisLockUtils.unLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId));
                return user;
            }
            log.info("新权重大于固定权重");
            //把分配满了的放进set集合中，对比排序队列中的人员是否都已经存在set中
            setOperations.add(buildKey(keyPrefix(), Allot.FINISH_SET, id), String.valueOf(user.getId()));
            Set<Object> setValue = setOperations.members(buildKey(keyPrefix(), Allot.FINISH_SET, id));
            Set<String> hashKeys = hashOperations.keys(buildKey(keyPrefix(), Allot.CURR_WEIGTH, id));
            // set中包含了排序队列人员，重置数据
            if (setValue.containsAll(hashKeys)) {
                log.info("进行数据重置");
                this.resetData(id);
            }
            // 释放锁
            redisLockUtils.unLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId));
            log.info("新权重大于固定权重，进行下一轮分配");
            return this.getOne(id);
        }
        return null;
    }

    /**
     * 重置数据
     */
    private void resetData(Long id) {
        log.info("从数据库查询固定队列");
        List<Object> userList = this.initData(id);
        //删除已经分配满了的队列
        redisTemplate.delete(buildKey(keyPrefix(), Allot.FINISH_SET, id));
        //删除已经分配的计数器
        redisTemplate.delete(buildKey(keyPrefix(), Allot.CURR_WEIGTH, id));
        // 删除原有排序队列
        redisTemplate.delete(buildKey(keyPrefix(), Allot.SORT_LIST, id));
        // 设置排序队列leftPush,先分配的放在队列右侧，所以选择左入队
        if (userList.size() > 0) {
            listOperations.leftPushAll(buildKey(keyPrefix(), Allot.SORT_LIST, id), userList.toArray());
        }
        log.info("数据写入完成");
    }

    /**
     * 获取唯一锁ID
     */
    protected Long getLockVersion(String fix, Long id) {
        return valueOperations.increment(buildKey(fix, Allot.LOCK_VERSION, id), 1L);
    }

    /**
     * 拼接key
     */
    protected String buildKey(String fix, String key, Long id) {
        return fix + ":" + id + ":" + key;
    }

    /**
     * 根据ID进行重置算法依赖的队列
     */
    public void listReset(Long id) {
        log.info("重置算法依赖的队列:{}", buildKey(keyPrefix(), RedisKeys.Allot.PROCESS_LOCK, id));
        Long processLockId = getLockVersion(keyPrefix(), id);
        // 没拿到锁，就2秒后重新去获取，直到拿到锁为止
        boolean processLock = redisLockUtils.setLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId), LOCK_EXPIRE, 2);
        // 获取这里的锁，就可以实现动态修改排序队列相关
        if (processLock) {
            // 进行队列重置
            log.info("拿到锁,进行队列重置,从数据库查询");
            List<Object> userList = this.initData(id);
            // 获取当前排序队列，获取第一个，新的排序队列同样指定到这个位置
            Object currSort = listOperations.index(buildKey(keyPrefix(), RedisKeys.Allot.SORT_LIST, id), 0);
            List<Object> fir = new ArrayList<>();
            List<Object> sec = new ArrayList<>();
            if (userList.size() > 0 && currSort != null) {
                // 开始遍历排序队列为要求的样子
                WeigthRoundDTO user = JsonUtil.fromJson(String.valueOf(currSort), WeigthRoundDTO.class);
                int location = 0;
                for (int i = 0; i < userList.size(); i++) {
                    WeigthRoundDTO round = JSONObject.parseObject(String.valueOf(userList.get(i)), WeigthRoundDTO.class);
                    if (user.getId().equals(round.getId())) {
                        // 找到指定位置
                        location = i;
                        break;
                    }
                }
                // list分割成两部分
                fir = userList.subList(0, location + 1);
                sec = userList.subList(location + 1, userList.size());
            }
            // 合并
            sec.addAll(fir);
            // 设置排序队列leftPush,先分配的放在队列右侧，所以选择左入队
            redisTemplate.delete(buildKey(keyPrefix(), RedisKeys.Allot.SORT_LIST, id));
            if (sec.size() > 0) {
                listOperations.leftPushAll(buildKey(keyPrefix(), RedisKeys.Allot.SORT_LIST, id), sec.toArray());
            }
            // 获取当前已经分配权重
            Set<String> currWeigthKeys = hashOperations.keys(buildKey(keyPrefix(), Allot.CURR_WEIGTH, id));

            if (currWeigthKeys.size() > 0) {
                // 获取排序队列中的用户ID
                Set<String> sortSet = new HashSet<>();
                for (Object o : sec) {
                    WeigthRoundDTO user = JsonUtil.fromJson(o.toString(), WeigthRoundDTO.class);
                    sortSet.add(user.getId().toString());
                }
                log.info("当前已经分配权重已经产生,获取排序队列中的用户ID List:{}", sortSet.toString());
                currWeigthKeys.forEach(userId -> {
                    // 去掉当前以分配权重中比排序队列中多的用户
                    if (!sortSet.contains(userId)) {
                        log.info(",去掉当前以分配权重中比排序队列中多的用户:{}", userId);
                        hashOperations.delete(buildKey(keyPrefix(), Allot.CURR_WEIGTH, id), userId);
                    }
                });
            }

            // 释放锁
            redisLockUtils.unLock(buildKey(keyPrefix(), Allot.PROCESS_LOCK, id), String.valueOf(processLockId));
        }
    }

}
