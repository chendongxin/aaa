package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.admin.model.dto.MessageCountDTO;
import com.hqjy.mustang.admin.model.entity.SysMessageEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/6/20 11:26
 */
public interface SysMessageService extends BaseService<SysMessageEntity, Long> {

    /**
     * 获取websocket地址
     */
    String getWebsocketUrl();

    /**
     * 发送通知
     */
    void sendNotice(Long userId, String title, String content, Serializable data);

    /**
     * 发送消息
     */
    void sendMessage(Long userId, String title, String content, Serializable data);

    /**
     * 主动断开本地连接
     */
    void disconnect(Long userId);

    /**
     * 用户查询消息列表
     */
    List<SysMessageEntity> getUserMessageListPage(PageQuery pageQuery);

    /**
     * 根据消息ID标记一条已读
     */
    void readUserMessage(Long msgId);

    /**
     * 根据类型标记所有已读
     */
    void readUserAllMessage(Integer type);

    /**
     * 根据消息ID删除消息
     */
    void deleteUserMessage(Long msgId);

    /**
     * 根据类型清空消息
     */
    void cleanUserMessage(Integer type);

    /**
     * 统计消息数量
     */
    MessageCountDTO countInfo();

    /**
     * 统计消息总数量
     */
    int totalCount();
}
