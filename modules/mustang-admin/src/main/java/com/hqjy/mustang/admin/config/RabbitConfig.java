package com.hqjy.mustang.admin.config;

import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/7/12 21:35
 */
@Configuration
@Slf4j
public class RabbitConfig {

    /**
     * 系统操作日志记录队列
     */
    @Bean
    public Queue sysLogQueue() {
        return new Queue(RabbitQueueConstant.SYS_LOG_QUEUE);
    }

    /**
     * 业务消息队列消费日志队列
     */
    @Bean
    public Queue logMessageQueue() {
        return new Queue(RabbitQueueConstant.LOG_MESSAGE_QUEUE);
    }

}