package com.hqjy.mustang.transfer.crm.config;

import com.hqjy.mustang.transfer.crm.consumer.NcDealConsumer;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xieyuqing
 * 消息队列配置
 */
@Configuration
public class NcRabbitConfig {

    @Value("${nc.rabbitmq.addresses}")
    private String address;

    @Value("${nc.rabbitmq.username}")
    private String username;

    @Value("${nc.rabbitmq.password}")
    private String password;

    @Value("${nc.rabbitmq.queuename.deal}")
    private String ncDeal;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean
    public NcDealConsumer ncDealConsumer() {
        return new NcDealConsumer();
    }

    @Bean
    public SimpleMessageListenerContainer mqMessageContainer() throws AmqpException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueueNames(ncDeal);
        container.setExposeListenerChannel(true);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        //设置确认模式为手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new NcDealConsumer());
        return container;
    }

}
