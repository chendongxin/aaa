package com.hqjy.mustang.admin.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_message 实体类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/20 14:49
 */
@Data
public class SysMessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long msgId;

    /**
     * 消息类型 2000：通知 2001：消息 2002：代办 type
     **/
    private Integer type;

    /**
     * 消息标题 title
     **/
    private String title;

    /**
     * 消息内容 content
     **/
    private String content;

    /**
     * 消息附带的数据 data
     **/
    private String data;

    /**
     * 是否已读 0：未读，1：已读 read_flag
     **/
    private Integer readFlag;

    /**
     * 消息接受者 user_id
     **/
    private Long userId;

    /**
     * 消息发送者 create_id
     **/
    private Long createId;

    /**
     * 创建时间 create_time
     **/
    private Date createTime;

    /**
     * 更新/阅读时间 update_time
     **/
    private Date updateTime;
}