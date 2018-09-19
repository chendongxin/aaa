package com.hqjy.mustang.common.model.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * log_message_queue 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/06 11:45
 */
@Data
public class LogMessageQueue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 消息队列名称
     */
    private String queueName;

    /**
     * mq中的JMSMessageID msg_id
     **/
    private String msgId;

    /**
     * 记录内容 此字段为json类型，利于查询 msg
     **/
    private String msg;

    /**
     * 消息类型：0：新消息 1：重试消息 type
     **/
    private Integer type;

    /**
     * 消息状态：0：待处理 1：已处理 2：异常商机 status
     **/
    private Integer status;

    /**
     * 备注：异常原因 memo
     **/
    private String memo;

    /**
     * 写入数据库时间 create_time
     **/
    private Date createTime;

    public LogMessageQueue() {
    }

    public LogMessageQueue(String queueName, String msgId, String msg, Integer status) {
        this.queueName = queueName;
        this.msgId = msgId;
        this.msg = msg;
        this.status = status;
    }
}