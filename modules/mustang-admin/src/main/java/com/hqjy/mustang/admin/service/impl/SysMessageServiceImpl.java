package com.hqjy.mustang.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.ConfigConstant;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.admin.dao.SysMessageDao;
import com.hqjy.mustang.admin.model.dto.MessageCountDTO;
import com.hqjy.mustang.admin.model.dto.MessageTypeCountDTO;
import com.hqjy.mustang.admin.model.dto.WsDTO;
import com.hqjy.mustang.admin.model.entity.SysMessageEntity;
import com.hqjy.mustang.admin.service.SysConfigService;
import com.hqjy.mustang.admin.service.SysMessageService;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 系统消息中心业务
 *
 * @author : heshuangshuang
 * @date : 2018/6/20 11:26
 */
@Service
@Slf4j
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessageDao, SysMessageEntity, Long> implements SysMessageService {
    private final static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 获取websocket地址
     */
    @Override
    public String getWebsocketUrl() {
        return sysConfigService.getConfig(ConfigConstant.WEBSOCKET_HOST);
    }

    /**
     * 主动断开连接
     */
    private static final int SOCKET_TYPE_DISCONNECT = 0;

    /**
     * 消息推送
     */
    private static final int SOCKET_TYPE_SEND = 1;

    /**
     * 系统发送默认id
     */
    private static final long SOURCE_DEFAULT = 0L;

    /**
     * websocket监听redis主题的名称,默认 websocket
     */
    @Value("${spring.redis.ws-topic-name}")
    private String wsTopicName;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 主动断开本地连接
     */
    @Override
    public void disconnect(Long userId) {
        send(null, SOCKET_TYPE_DISCONNECT, SOURCE_DEFAULT, userId, null);
    }

    /**
     * 发送通知，data里面存放具体消息内容
     */
    @Override
    public void sendNotice(Long userId, String title, String content, Serializable data) {
        SysMessageEntity messageEntity = new SysMessageEntity();
        messageEntity.setUserId(userId);
        messageEntity.setCreateId(SOURCE_DEFAULT);
        messageEntity.setTitle(title);
        messageEntity.setContent(content);
        messageEntity.setData(JsonUtil.toJson(data));
        messageEntity.setReadFlag(0);
        messageEntity.setType(Constant.WsData.NOTICE.getValue());
        // 保存到消息中心表
        int count = baseDao.save(messageEntity);
        if (count > 0) {
            // 发送webSocket消息
            send(Constant.WsData.NOTICE, userId, messageEntity);
        }
    }

    /**
     * 发送消息
     */
    @Override
    public void sendMessage(Long userId, String title, String content, Serializable data) {
        SysMessageEntity messageEntity = new SysMessageEntity();
        messageEntity.setUserId(userId);
        messageEntity.setCreateId(SOURCE_DEFAULT);
        messageEntity.setTitle(title);
        messageEntity.setContent(content);
        messageEntity.setData(JsonUtil.toJson(data));
        messageEntity.setReadFlag(0);
        messageEntity.setType(Constant.WsData.MESSAGE.getValue());
        // 保存到消息中心表
        int count = baseDao.save(messageEntity);
        if (count > 0) {
            // 发送webSocket消息
            send(Constant.WsData.MESSAGE, userId, messageEntity);
        }
    }

    /**
     * 发送代办
     */
    private void sendTodo() {

    }


    /**
     * 系统消息推送到用户
     */
    private void send(Constant.WsData type, Long userId, Serializable result) {
        send(type, SOCKET_TYPE_SEND, SOURCE_DEFAULT, userId, result);
    }

    private void send(Constant.WsData type, int operation, Long source, Long destination, Serializable result) {
        WsDTO wsDTO = new WsDTO();
        if (type != null) {
            // 不同实体对应不同消息类型,类型编号 从 2000 开始，1000预留为ws的固定类型
            wsDTO.setType(type.getValue());
        }
        wsDTO.setSource(String.valueOf(source));
        wsDTO.setDestination(String.valueOf(destination));
        wsDTO.setOperation(operation);
        wsDTO.setData(result);
        redisTemplate.convertAndSend(wsTopicName, GSON.toJson(wsDTO));
    }


    /**
     * 用户查询消息列表
     */
    @Override
    public List<SysMessageEntity> getUserMessageListPage(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        pageQuery.put("userId", ShiroUtils.getUserId());
        return baseDao.findMessageListPage(pageQuery);
    }

    /**
     * 根据消息ID标记一条已读
     */
    @Override
    public void readUserMessage(Long msgId) {
        baseDao.updateReadByMsgId(ShiroUtils.getUserId(), msgId);
    }

    /**
     * 根据类型标记所有已读
     */
    @Override
    public void readUserAllMessage(Integer type) {
        baseDao.updateReadByType(ShiroUtils.getUserId(), type);
    }

    /**
     * 根据消息ID删除消息
     */
    @Override
    public void deleteUserMessage(Long msgId) {
        baseDao.deleteByMsgId(ShiroUtils.getUserId(), msgId);
    }

    /**
     * 根据类型清空消息
     */
    @Override
    public void cleanUserMessage(Integer type) {
        baseDao.deleteByType(ShiroUtils.getUserId(), type);
    }


    /**
     * 统计消息总数量
     */
    @Override
    public int totalCount() {
        return baseDao.totalCount(ShiroUtils.getUserId());
    }

    /**
     * 统计消息数量
     */
    @Override
    public MessageCountDTO countInfo() {
        List<MessageTypeCountDTO> typeCountDTOS = baseDao.countInfo(ShiroUtils.getUserId());
        MessageCountDTO countDTO = new MessageCountDTO();
        typeCountDTOS.forEach(typeCount -> {
            int type = typeCount.getType();
            countDTO.setTotal(countDTO.getTotal() + typeCount.getCount());
            if (Constant.WsData.MESSAGE.equals(type)) {
                countDTO.setMessage(typeCount.getCount());
            } else if (Constant.WsData.NOTICE.equals(type)) {
                countDTO.setNotice(typeCount.getCount());
            } else if (Constant.WsData.TODO.equals(type)) {
                countDTO.setTodo(typeCount.getCount());
            }
        });
        return countDTO;
    }

}
