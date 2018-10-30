package com.hqjy.mustang.transfer.sms.service;

import com.hqjy.mustang.transfer.sms.model.dto.SmsResultDTO;
import com.hqjy.mustang.transfer.sms.model.dto.SmsResultListDTO;
import com.hqjy.mustang.transfer.sms.model.dto.SmsStatusDTO;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 16:18
 */
public interface SmsApiService {
    SmsResultDTO sendSms(TransferSmsEntity smsEntity);
    SmsResultDTO sendReport();
}
