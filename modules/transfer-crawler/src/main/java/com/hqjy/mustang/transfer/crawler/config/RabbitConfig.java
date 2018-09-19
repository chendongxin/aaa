package com.hqjy.mustang.transfer.crawler.config;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import com.hqjy.mustang.transfer.crawler.constant.CrawlerConstant;
import com.hqjy.mustang.transfer.crawler.service.TrasferEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TrasferEmailService trasferEmailService;

    /**
     * 定制化amqp模版      可根据需要定制多个
     * <p>
     * <p>
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     */
    @Bean
    public AmqpTemplate amqpTemplate() {
        //          使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        //开启returncallback  yml 需要 配置    publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationIdString();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String corrId = correlationData.getId();
            if (ack) {
                log.debug("消息发送到exchange成功,id: {}", corrId);
            } else {
                log.debug("消息: {} 发送到 exchange 失败,原因: {}", corrId, cause);
                // 发送失败，修改简历状态
                if (corrId.startsWith(CrawlerConstant.RESUME_MESSAGE_ID)) {
                    trasferEmailService.updateStatus(Long.valueOf(corrId.replaceAll(CrawlerConstant.RESUME_MESSAGE_ID, "")), Constant.Status.DISABLE.getValue());
                }
            }
        });
        return rabbitTemplate;
    }

    /**
     * 声明一个队列 支持持久化.
     */
    @Bean
    public Queue transferBusinessQueue() {
        return QueueBuilder.durable(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE).build();
    }

}