package com.hqjy.mustang.allot.listener;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.gson.reflect.TypeToken;
import com.hqjy.mustang.allot.constant.AllotCode;
import com.hqjy.mustang.allot.exception.MqException;
import com.hqjy.mustang.common.model.allot.MessageQueueDTO;
import com.hqjy.mustang.allot.service.MessageQueueService;
import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.base.utils.Tools;
import com.hqjy.mustang.common.model.admin.LogMessageQueue;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * 消息队列消费监听器
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 11:20
 */
@Slf4j
@Component
public class TrasferMessageListener {
    /**
     * 待处理标识
     */
    private final static Integer STATUS_PENDING = 0;
    /**
     * 已处理标识
     */
    private final static Integer STATUS_PROCESSED = 1;
    /**
     * 异常标识
     */
    private final static Integer STATUS_EXCEPTION = 2;
    /**
     * 新消息类型
     */
    private final static Integer TYPE_NEW = 0;
    /**
     * 重试消息类型
     */
    private final static Integer TYPE_REDELIVERED = 1;

    @Lazy
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private MessageQueueService messageQueueService;

    /**
     * 定义消息数据结构{"msgType":2,"msgBody":{"source":1,"phone":"17600222251","name":"自动分配测试","memo":"重单测试","url":"http://m.bd.s8.zk.hqjy.cn"}}
     */
    @RabbitListener(queues = {RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE})
    public void onMessage(Message message, Channel channel) throws IOException {

        // 设置messageId  记录日志
        String jsonMessage = new String(message.getBody());
        log.info("消息队列消费监听器：{}", jsonMessage);
        if (StringUtils.isEmpty(jsonMessage)) {
            return;
        }
        String msgId = message.getMessageProperties().getHeaders().getOrDefault("spring_listener_return_correlation", UUID.randomUUID()).toString();

        // 设置messageId  记录日志
        LogMessageQueue mqLog = new LogMessageQueue(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE, msgId, JsonUtil.toJson(jsonMessage), STATUS_PENDING);

        try {
            // 解析json消息为对象
            MessageQueueDTO messageQueueDTO = JsonUtil.fromJson(jsonMessage, new TypeToken<MessageQueueDTO>() {
            }.getType());

            //设置 消息内容
            mqLog.setMsg(JsonUtil.toJson(messageQueueDTO));

            if (message.getMessageProperties().isRedelivered()) {
                mqLog.setType(TYPE_REDELIVERED);
                log.info("重试MQ消息：{}", jsonMessage);
            } else {
                mqLog.setType(TYPE_NEW);
                log.info("新的MQ消息：{}", jsonMessage);
            }

            // 处理消息入口
            Boolean result = messageQueueService.processMessage(msgId, messageQueueDTO);
            //处理成功，向activeMQ 确认接收消息
            if (result) {
                mqLog.setStatus(STATUS_PROCESSED);
                log.info("消息处理成功:{}", msgId);
            } else {
                log.error("没有进行分配:{}", msgId);
            }
        } catch (MqException e) {
            int code = e.getCode();
            String msg = e.getMsg();
            log.error(msg);
            mqLog.setMemo(JSONUtils.toJSONString(msg));
            if (code == AllotCode.MQ_REDELIVERED.getCode()) {
                // 商机重试
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                // 标识为待处理
                mqLog.setStatus(STATUS_PENDING);
            } else {
                // 标识为异常商机
                mqLog.setStatus(STATUS_EXCEPTION);
            }
        } catch (Exception e) {
            //消息有毒，需要处理，不能返回给activeMQ,因为会一直不能被消费
            log.error("处理MQ消息发生未知异常,详情请查看日志 -> msgId:{},error:{}", mqLog.getMsgId(), e);
            mqLog.setStatus(STATUS_EXCEPTION);
            mqLog.setMemo(JsonUtil.toJson(Tools.exceptionInfo(e)));
        }
        // 发送mq日志消息记录
        rabbitTemplate.convertAndSend(RabbitQueueConstant.LOG_MESSAGE_QUEUE, JsonUtil.toJson(mqLog));
        // 确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

        System.out.println("message.getMessageProperties().getDeliveryTag()====>" + message.getMessageProperties().getDeliveryTag());
    }

}
