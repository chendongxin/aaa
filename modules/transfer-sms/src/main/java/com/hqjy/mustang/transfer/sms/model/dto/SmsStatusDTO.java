package com.hqjy.mustang.transfer.sms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 17:51
 */
@Data
public class SmsStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通道号
     */
    private int channelId;

    /**
     * 通道基础号
     */
    private Long destTerminalId;

    /**
     * 接收时间格式为(yyyyMMddHHmmss)
     */
    private String doneTime;

    /**
     * 客户提交时的smsId
     */
    private String msgId;

    /**
     * 状态码,是否成功失败,0成功其它失败
     */
    private int result;

    /**
     * 直接返回网关的状态值
     */
    private String resultValue;

    /**
     * 源消息ID同msg_Id
     */
    private int srcSmsId;

    /**
     * 发送手机号
     */
    private String srcTerminalId;

    /**
     * 短息提交时间格式为(yyyyMMddHHmmss)
     */
    private String submitTime;

    private String serviceCode;

    private String serviceCodeAdd;

    private String memo;

}
