package com.hqjy.mustang.transfer.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.sms.constant.SmsConstant;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsDao;
import com.hqjy.mustang.transfer.sms.model.dto.SmsResultDTO;
import com.hqjy.mustang.transfer.sms.model.dto.SmsStatusDTO;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.service.SmsApiService;
import com.hqjy.mustang.transfer.sms.service.TransferSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:47
 */
@Service
@Slf4j
public class TransferSmsServiceImpl extends BaseServiceImpl<TransferSmsDao, TransferSmsEntity, Long> implements TransferSmsService {

    private Pattern PATTERN_PHONE = Pattern.compile("^$|^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Autowired
    private SmsApiService smsApiService;

    /**
     * 短信保存
     */
    @Override
    public int saveSms(TransferSmsEntity smsEntity) {
        // 数据初始化
        smsEntity.setCreateUserId(ShiroUtils.getUserId());
        smsEntity.setCreateUserName(ShiroUtils.getUserName());
        smsEntity.setStatus(SmsConstant.SendStatus.AWAIT.getCode());
        smsEntity.setStatusValue(SmsConstant.SendStatus.AWAIT.getValue());

        String phones = StringUtils.trim(smsEntity.getPhone());
        String[] phoneList = phones.split(",");
        int count = 0;
        for (String phone : phoneList) {
            // 验证手机号合法
            if (PATTERN_PHONE.matcher(phone).find()) {
                TransferSmsEntity newSmsEntity = PojoConvertUtil.convert(smsEntity, TransferSmsEntity.class);
                newSmsEntity.setPhone(phone);
                count += baseDao.save(newSmsEntity);
            }
        }
        return count;
    }

    /**
     * 短信执行发送
     */
    @Override
    public int smsSend(Long[] ids) {
        int count = 0;
        for (Long id : ids) {
            TransferSmsEntity smsEntity = baseDao.findOne(id);
            if (smsEntity != null) {
                SmsResultDTO resultDTO = smsApiService.sendSms(smsEntity);
                boolean result = Optional.ofNullable(resultDTO).map(r -> r.getResult() == 0).orElse(false);
                if (result) {
                    // 提交成功，修改状态为1
                    smsEntity.setStatus(SmsConstant.SendStatus.SUBMIT.getCode());
                    smsEntity.setStatusValue(SmsConstant.SendStatus.SUBMIT.getValue());
                    // 执行发送时间
                    smsEntity.setSendTime(new Date());
                    baseDao.update(smsEntity);
                }
                count += result ? 1 : 0;
            }
        }
        return count;
    }

    /**
     * 短信发送回调
     */
    @Override
    public void smsReport(String params) {
        if (StringUtils.isNotEmpty(params)) {
            try {
                params = StringUtils.cutPrefix(URLDecoder.decode(params, "utf-8"), "Report=");
                log.info("sms发送状态回调：{}", params);
                List<SmsStatusDTO> list = JSON.parseArray(params, SmsStatusDTO.class);
                list.forEach(s -> {
                    TransferSmsEntity smsEntity = baseDao.findOne(Long.valueOf(s.getMsgId()));
                    if (smsEntity != null && smsEntity.getStatus() != 2) {
                        smsEntity.setStatus(s.getResult() == 0 ? SmsConstant.SendStatus.SUCCESS.getCode() : s.getResult());
                        smsEntity.setStatusValue(SmsConstant.SendStatus.SUCCESS.handleSendStatusName(smsEntity.getStatus()).getValue());
                        smsEntity.setSubmitTime(dateFormat(s.getSubmitTime()));
                        smsEntity.setDoneTime(dateFormat(s.getDoneTime()));
                        smsEntity.setUpdateUserId(0L);
                        smsEntity.setUpdateUserName("系统回调");
                        baseDao.update(smsEntity);
                    }
                });
            } catch (UnsupportedEncodingException e) {
                log.error("短信发送回调处理异常:{}", e);
            }
        }
    }

    /**
     * sms短信回复回调
     */
    @Override
    public void smsReply(String params) {
        if (StringUtils.isNotEmpty(params)) {
            try {
                params = StringUtils.cutPrefix(URLDecoder.decode(params, "utf-8"), "MO=");
                log.info("sms短信回复回调：{}", params);
            } catch (UnsupportedEncodingException e) {
                log.error("短信回复回调处理异常:{}", e);
            }
        }

    }

    private Date dateFormat(String dateStr) {
        return Date.from(LocalDateTime.parse(dateStr, df).atZone(ZoneId.systemDefault()).toInstant());
    }
}
