package com.hqjy.mustang.allot.service;

import com.hqjy.mustang.common.model.allot.MessageQueueDTO;

/**
 * 消息队列消费逻辑
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:20
 */
public interface MessageQueueService {

    /**
     * 处理消息
     */
    Boolean processMessage(String msgId, MessageQueueDTO mqDTO);
}
