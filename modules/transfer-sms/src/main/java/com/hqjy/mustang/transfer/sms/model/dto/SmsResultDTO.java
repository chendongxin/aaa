package com.hqjy.mustang.transfer.sms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 16:54
 */
@Data
public class SmsResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int result;

    private String body;

    private String description;
}
