package com.hqjy.mustang.allot.service.impl;

import com.hqjy.mustang.allot.constant.AllotCode;
import com.hqjy.mustang.allot.exception.MqException;
import com.hqjy.mustang.common.model.allot.MessageQueueDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.allot.service.AbstractHandleService;
import com.hqjy.mustang.allot.service.MessageQueueService;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 消息队列消费入口
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:20
 */
@Service
@Slf4j
public class MessageQueueServiceImpl implements MessageQueueService {


    /**
     * 招转分配处理
     */
    @Resource(name = "transferHandleService")
    private AbstractHandleService<TransferAllotCustomerEntity> transferHandleService;

    /**
     * 处理消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean processMessage(String msgId, MessageQueueDTO mqDTO) {
        // 消息类型
        int mqType = mqDTO.getMsgType();
        // 消息内容
        String content = JsonUtil.toJson(mqDTO.getMsgBody());
        switch (mqType) {
            // 自考商机类型
            case 2:
                return false;
            // 招转商机类型
            case 3:
                // 转换消息类型
                return transferHandleService.start(JsonUtil.fromJson(content, TransferAllotCustomerEntity.class));
            //其他消息
            default:
                // 抛出无法处理的消息异常
                throw new MqException(AllotCode.MQ_TYPE_ERROR);
        }
    }

}
