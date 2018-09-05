package com.hqjy.transfer.allot.service.impl;

import com.hqjy.transfer.allot.constant.AllotCode;
import com.hqjy.transfer.allot.exception.MqException;
import com.hqjy.transfer.allot.model.dto.MessageQueueDTO;
import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;
import com.hqjy.transfer.allot.model.dto.MessageQueueDTO;
import com.hqjy.transfer.allot.service.BizHandleService;
import com.hqjy.transfer.allot.service.MessageQueueService;
import com.hqjy.transfer.common.base.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列消费入口
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:20
 */
@Service
@Slf4j
public class MessageQueueServiceImpl implements MessageQueueService {

    @Autowired
    private BizHandleService bizAllotService;

    /**
     * 处理消息
     */
    @Override
    public Boolean processMessage(String msgId, MessageQueueDTO mqDTO) {
        // 消息类型
        int mqType = mqDTO.getMsgType();
        // 消息内容
        String content = JsonUtil.toJson(mqDTO.getMsgBody());
        switch (mqType) {
            // 自考商机类型
            case 2:
                // 转换消息类型
                return bizAllotService.process(msgId, JsonUtil.fromJson(content, AllotCustomerEntity.class));
            //其他消息
            default:
                // 抛出无法处理的消息异常
                throw new MqException(AllotCode.MQ_TYPE_ERROR);
        }
    }

}
