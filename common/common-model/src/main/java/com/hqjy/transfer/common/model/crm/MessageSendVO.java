package com.hqjy.transfer.common.model.crm;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/9/5 19:59
 */
@Data
public class MessageSendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String title;
    private String content;
    private Serializable data;
}
