package com.hqjy.mustang.allot.config;

import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
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
     * 声明一个队列 支持持久化.
     */
    @Bean("transferBusinessQueue")
    public Queue transferBusinessQueue() {
        return QueueBuilder.durable(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE).build();
    }
    /**
     * 系统操作日志记录队列
     */
  /*  @Bean
    public Queue transferBusinessQueue() {
        return new Queue(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE);
    }*/

}