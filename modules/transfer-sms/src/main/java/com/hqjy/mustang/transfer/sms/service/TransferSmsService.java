package com.hqjy.mustang.transfer.sms.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.sms.model.dto.SmsResultDTO;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:46
 */
public interface TransferSmsService extends BaseService<TransferSmsEntity, Long> {
    /**
     * 短信保存并发送
     */
    int saveAndSend(TransferSmsEntity smsEntity);

    /**
     * 短信保存
     */
    int saveSms(TransferSmsEntity smsEntity);

    /**
     * 短信执行发送
     */
    int smsSend(Long[] ids);

    /**
     * 短信发送回调
     */
    void smsReport(Long[] ids);

    /**
     * sms短信回复回调
     */
    void smsReply(String params);
}
