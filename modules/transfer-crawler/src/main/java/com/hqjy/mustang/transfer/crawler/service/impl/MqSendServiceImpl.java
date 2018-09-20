package com.hqjy.mustang.transfer.crawler.service.impl;

import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import com.hqjy.mustang.common.model.allot.MessageQueueDTO;
import com.hqjy.mustang.transfer.crawler.constant.CrawlerConstant;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import com.hqjy.mustang.transfer.crawler.service.MqSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : heshuangshuang
 * @date : 2018/9/13 9:46
 */
@Service
@Slf4j
public class MqSendServiceImpl implements MqSendService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送到Mq
     */
    @Override
    public void send(TransferResumeEntity resumeEntity) {
        CorrelationData correlationData = new CorrelationData(CrawlerConstant.RESUME_MESSAGE_ID + resumeEntity.getId().toString());
        MessageQueueDTO messageQueueDTO = new MessageQueueDTO();
        // 数据字典显示和值，待处理 TODO
        resumeEntity.setWorkExperience(null);
        messageQueueDTO.setMsgBody(resumeEntity);
        // 3代表招转的商机类型
        messageQueueDTO.setMsgType(3);
        rabbitTemplate.convertAndSend(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE, messageQueueDTO, correlationData);
    }
}
