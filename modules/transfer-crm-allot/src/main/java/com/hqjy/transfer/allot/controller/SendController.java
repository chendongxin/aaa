package com.hqjy.transfer.allot.controller;

import com.hqjy.transfer.common.base.utils.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/7/12 21:41
 */
@RestController
@RequestMapping("/test/rabbit")
public class SendController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试广播模式.
     */
    @RequestMapping("/fanout")
    public R send(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
        return R.ok();
    }

    /**
     * 测试Direct模式.
     */
    @RequestMapping("/direct")
    public R direct(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
        return R.ok();
    }
}