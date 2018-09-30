package com.hqjy.mustang.transfer.sms.model.dto;

import lombok.Data;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 16:54
 */
@Data
public class SmsResultDTO {
    private int result;

    private String body;

    private String description;
}
