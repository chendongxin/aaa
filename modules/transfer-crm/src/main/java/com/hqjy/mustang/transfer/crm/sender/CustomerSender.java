package com.hqjy.mustang.transfer.crm.sender;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

/**
 * @author xieyuqing
 * @ description 客户生产者
 * @ date create in 2018/6/19 9:44
 */
@Configuration
@Slf4j
public class CustomerSender {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private ActiveMQQueue queue;

//    @Autowired
//    public void setJmsMessagingTemplate(JmsMessagingTemplate jmsMessagingTemplate) {
//        this.jmsMessagingTemplate = jmsMessagingTemplate;
//    }
//
//    @Autowired
//    public void setQueue(ActiveMQQueue queue) {
//        this.queue = queue;
//    }

    @Async
    public void send(String message) {
        //发送队列消息
        this.jmsMessagingTemplate.convertAndSend(this.queue, message);
        log.info("发送MQ消息:分配客户算法请求数据->{}" + message);
    }
}
