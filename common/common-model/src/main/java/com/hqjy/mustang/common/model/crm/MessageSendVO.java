package com.hqjy.mustang.common.model.crm;

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

    public MessageSendVO() {
    }

    public MessageSendVO(Long userId, String title, String content, Serializable data) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.data = data;
    }
}
