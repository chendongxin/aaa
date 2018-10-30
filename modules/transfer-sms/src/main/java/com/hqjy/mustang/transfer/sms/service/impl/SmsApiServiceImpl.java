package com.hqjy.mustang.transfer.sms.service.impl;

import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsDao;
import com.hqjy.mustang.transfer.sms.model.dto.SmsResultListDTO;
import com.hqjy.mustang.transfer.sms.model.dto.SmsStatusDTO;
import com.hqjy.mustang.transfer.sms.utils.SmsSendUtils;
import com.hqjy.mustang.transfer.sms.model.dto.SmsResultDTO;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.service.SmsApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 16:18
 */
@Service
@Slf4j
public class SmsApiServiceImpl implements SmsApiService {

    @Autowired
    private TransferSmsDao transferSmsDao;
    /**
     * 用户名
     */
    @Value("${sms.youxinyun.userName}")
    private String userName;

    /**
     * 密码
     */
    @Value("${sms.youxinyun.password}")
    private String password;

    /**
     * 发送短信地址
     */
    @Value("${sms.youxinyun.api.sendSms}")
    private String sendSmsUrl;

    /**
     * 短信状态回调地址
     */
    @Value("${sms.youxinyun.callback}")
    private String callbackUrl;

    @Override
    public SmsResultDTO sendSms(TransferSmsEntity smsEntity) {
        try {
            // 时间戳
            String timestemp = SmsSendUtils.getTimestemp();
            // 加密
            String key = SmsSendUtils.getKey(userName, password, timestemp);
            Long smsId = smsEntity.getId();
            Hashtable<String, String> header = new Hashtable<>();
            header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // post请求
            String params = "UserName=" + userName + "&" +
                    "Key=" + key + "&" +
                    "Timestemp=" + timestemp + "&" +
                    "Content=" + URLEncoder.encode(smsEntity.getContent(), "utf-8") + "&" +
                    "CharSet=utf-8&" +
                    "Mobiles=" + smsEntity.getPhone() + "&" +
                    "ExpandNumber=" + smsEntity.getDeptId() + "&" +
                    "SMSID=" + smsId;
            InputStream _InputStream = SmsSendUtils.SendMessage(params.getBytes(StandardCharsets.UTF_8), header, sendSmsUrl);
            String response = SmsSendUtils.GetResponseString(_InputStream, "utf-8");
            // 响应的包体
            log.info("SMS响应的包体->{}", response);
            if (StringUtils.isNotEmpty(response)) {
                //保存时间戳，方便后面更新状态
                smsEntity.setTimeStemp(timestemp);
                transferSmsDao.saveTimeStemp(smsEntity);
                return JsonUtil.parseObject(response, SmsResultDTO.class);
            }
        } catch (Exception e) {
            log.error("sms发送短信接口异常：{}", e);
        }
        return null;
    }

    @Override
    public SmsResultDTO sendReport(Long id){
        try {
            //查询发送时的时间戳
            TransferSmsEntity smsEntity = transferSmsDao.findOne(id);
            //时间戳
            String timestemp = SmsSendUtils.getTimestemp();
            if(null != smsEntity && StringUtils.isNotEmpty(smsEntity.getTimeStemp())){
                // 如果数据库中存在，则取数据库中的时间戳
              timestemp = smsEntity.getTimeStemp();
            }
            // 加密
            String key = SmsSendUtils.getKey(userName, password, timestemp);
            Hashtable<String, String> header = new Hashtable<>();
            header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            // post请求
            String params = "UserName=" + userName + "&" +
                    "Key=" + key + "&" +
                    "Timestemp=" + timestemp ;
            InputStream _InputStream = SmsSendUtils.SendMessage(params.getBytes(StandardCharsets.UTF_8), header, callbackUrl);
            String response = SmsSendUtils.GetResponseString(_InputStream, "utf-8");
            // 响应的包体
            log.info("SMS响应的包体->{}", response);
            if (StringUtils.isNotEmpty(response)) {
                return JsonUtil.parseObject(response, SmsResultDTO.class);
            }

        } catch (Exception e) {
            log.error("sms短信状态回调接口异常：{}", e);
        }
        return null;
    }


}
