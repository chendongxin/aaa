package com.hqjy.mustang.admin.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshuangshuang
 * @date : 2018/6/20 15:29
 */
@Data
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 是否可跳转
     */
    private boolean jump;

    /**
     * 跳转依赖的key
     */
    private Serializable key;

    /**
     * 消息原生数据
     */
    private Serializable data;

    public MessageDTO() {
    }

    public MessageDTO(boolean jump, Serializable key) {
        this.jump = jump;
        this.key = key;
    }

    public MessageDTO(boolean jump, Serializable key, Serializable data) {
        this.jump = jump;
        this.key = key;
        this.data = data;
    }
}
