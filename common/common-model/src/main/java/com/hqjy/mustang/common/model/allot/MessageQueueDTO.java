package com.hqjy.mustang.common.model.allot;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息队列数据对象
 *
 * @author : heshuangshuang
 * @date : 2018/6/6 14:12
 */
@Data
public class MessageQueueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    public int msgType;

    /**
     * 消息体
     */
    private Object msgBody;
}
