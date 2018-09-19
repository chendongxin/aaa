package com.hqjy.mustang.transfer.crawler.service;

import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;

/**
 * @author : heshuangshuang
 * @date : 2018/9/13 9:46
 */
public interface MqSendService {
    /**
     * 发送到Mq
     */
    void send(TransferResumeEntity resumeEntity);
}
