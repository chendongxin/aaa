package com.hqjy.mustang.admin.listener;

import com.hqjy.mustang.admin.model.entity.LogMessageQueueEntity;
import com.hqjy.mustang.admin.model.entity.SysLogEntity;
import com.hqjy.mustang.admin.service.LogMessageQueueService;
import com.hqjy.mustang.admin.service.SysLogService;
import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/7/12 21:40
 */
@Component
@Slf4j
public class RabbitMqListener {

    @Autowired
    private LogMessageQueueService logMessageQueueService;

    @Autowired
    private SysLogService sysLogService;

    /**
     * 系统操作日志记录
     */
    @RabbitListener(queues = {RabbitQueueConstant.SYS_LOG_QUEUE})
    public void sysLog(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        if (StringUtils.isNotEmpty(msg)) {
            log.debug("系统操作日志记录： " + new String(message.getBody()));
            try {
                SysLogEntity sysLogEntity = JsonUtil.fromJson(msg, SysLogEntity.class);
                sysLogService.save(sysLogEntity);
            } catch (Exception e) {
                log.error("系统操作日志记录失败：{}", e.getMessage());
                log.error("失败详情：", e);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }


    /**
     * 业务消息队列消费日志
     */
    @RabbitListener(queues = {RabbitQueueConstant.LOG_MESSAGE_QUEUE})
    public void logMessage(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        if (StringUtils.isNotEmpty(msg)) {
            log.debug("业务消息队列消费日志： " + new String(message.getBody()));
            try {
                LogMessageQueueEntity logMessageQueueEntity = JsonUtil.toObject(msg, LogMessageQueueEntity.class);
                logMessageQueueService.save(logMessageQueueEntity);
            } catch (Exception e) {
                log.error("业务消息队列消费日志记录失败：{}", e.getMessage());
                log.error("失败详情：", e);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
