package com.hqjy.mustang.transfer.crm.task;

import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisLock;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/7/6 9:49
 */
@Component
public class ScheduledTask {

    private RedisTemplate redisTemplate;
    private TransferProcessService transferProcessService;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setBizProcessService(TransferProcessService transferProcessService) {
        this.transferProcessService = transferProcessService;
    }

    /**
     * 招转定时回收客户任务,执行时间：每天23:59:59
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void RecyclingTask() {
        RedisLock lock = new RedisLock(redisTemplate, RedisKeys.Business.recycleLock(RedisKeys.Business.RECYCLE_LOCK_KEY), 1, 60000);
        try {
            //这里锁等待时间设置为1,即几乎无须等待，这里根据业务需求，当天只能有一个线程执行该任务
            //多个线程无须经过锁等待，同时去获取锁，如果redis锁不存在,，这个线程拿到锁，返回true执行回收任务；其他线程均返回false,同时结束线程。
            if (lock.lock()) {
                transferProcessService.RecyclingCustomer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
