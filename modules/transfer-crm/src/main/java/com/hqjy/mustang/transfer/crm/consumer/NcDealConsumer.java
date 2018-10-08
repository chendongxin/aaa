package com.hqjy.mustang.transfer.crm.consumer;


import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.base.utils.Tools;
import com.hqjy.mustang.transfer.crm.feign.LogMessageQueueFeign;
import com.hqjy.mustang.transfer.crm.model.dto.NcDealMsgDTO;
import com.hqjy.mustang.transfer.crm.model.entity.LogMessageQueueEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDealService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * rabbitmq消费,NC成交消费者
 * <p>
 * DIRECT模式.,nc成交 返回数据：{"nc_pk":"0001DS100000000OT84D","state":"Y","min_reg_date":"2018-06-27 15:50:33"}
 *
 * @author : xyq
 * @date : 2018年9月30日15:26:15
 */
@Slf4j
@Component
public class NcDealConsumer implements ChannelAwareMessageListener {

    /**
     * 待处理标识
     */
    private final static Integer STATUS_PENDING = 0;
    /**
     * 已处理标识
     */
    private final static Integer STATUS_PROCESSED = 1;
    /**
     * 异常标识
     */
    private final static Integer STATUS_EXCEPTION = 2;

    private TransferCustomerDealService dealService;

    private LogMessageQueueFeign queueFeign;

    @Autowired
    public void setQueueFeign(LogMessageQueueFeign queueFeign) {
        this.queueFeign = queueFeign;
    }

    @Autowired
    public void setDealService(TransferCustomerDealService dealService) {
        this.dealService = dealService;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        String json = new String(message.getBody());
        log.info("NC成交消息: {}", json);
        LogMessageQueueEntity mqLog = new LogMessageQueueEntity("Nc_Deal:" + System.currentTimeMillis() + "-" + String.valueOf(message.hashCode()), JsonUtil.toJson(json), STATUS_PENDING);
        try {
            NcDealMsgDTO ncDeal = JSON.parseObject(json, NcDealMsgDTO.class);
            mqLog.setMsg(JsonUtil.toJson(ncDeal));
            if (StringUtils.isNotEmpty(ncDeal.getNcPk())) {
                int i = dealService.processDealMsg(ncDeal);
                if (i > 0) {
                    log.info("消费NC成交消息：正常处理成功");
                    mqLog.setStatus(STATUS_PROCESSED);
                    mqLog.setMemo(JsonUtil.toJson("消费NC成交消息：正常处理成功"));
                } else {
                    log.error("消费NC成交消息：处理失败，系统未查询到对应的NC主键的招转客户");
                    mqLog.setStatus(STATUS_EXCEPTION);
                    mqLog.setMemo(JsonUtil.toJson("消费NC成交消息：处理失败，系统未查询到对应的NC主键的招转客户"));
                }
            } else {
                log.info("消费NC成交消息：NC成交消息中NC主键未空");
                mqLog.setStatus(STATUS_EXCEPTION);
                mqLog.setMemo(JsonUtil.toJson("消费NC成交消息：NC成交消息中NC主键未空"));
            }
        } catch (Exception e) {
            mqLog.setStatus(STATUS_EXCEPTION);
            mqLog.setMemo(JsonUtil.toJson(Tools.exceptionInfo(e)));
            log.info("NC成交消息处理异常: {}---{}", json, e.getMessage());
        }
        //确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        //写入mq消费日志
        queueFeign.save(mqLog);
    }
}
