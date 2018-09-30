package com.hqjy.mustang.transfer.sms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 17:51
 */
@Data
public class SmsReplyDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 手机号
     */
    private String srcTerminalId;
    /**
     * msg_content
     */
    private String msgContent;
    /**
     * 提交时间
     */
    private String submitTime;

    /**
     * 通道号+特服号+扩展码
     */
    private String destTerminalId;

    private String doneTime;

}
