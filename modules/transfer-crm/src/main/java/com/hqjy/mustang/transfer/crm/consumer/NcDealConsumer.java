package com.hqjy.mustang.transfer.crm.consumer;


import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.transfer.crm.model.dto.NcDealMsgDTO;
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

    private TransferCustomerDealService dealService;

    @Autowired
    public void setDealService(TransferCustomerDealService dealService) {
        this.dealService = dealService;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        String json = new String(message.getBody());
        try {
            NcDealMsgDTO ncDealMsgDTO = JSON.parseObject(json, NcDealMsgDTO.class);
            int i = dealService.processDealMsg(ncDealMsgDTO);
            if (i > 0) {
                log.info("处理成功");
            } else {
                log.error("处理失败");
            }
        } catch (Exception e) {
            log.error("消息处理异常");
        }
        //写入mq消费日志
        //确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
