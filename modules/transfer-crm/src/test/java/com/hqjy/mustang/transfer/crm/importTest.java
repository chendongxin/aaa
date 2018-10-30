package com.hqjy.mustang.transfer.crm;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransferCrmApplication.class)
public class importTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("hello", 123, true));
        rabbitTemplate.convertAndSend(RabbitQueueConstant.MUSTANG_TRANSFER_QUEUE, JSON.toJSONString(JSON.toJSON(map)));
    }

    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("${pom.teacher.plan.sync.kd}");
        System.out.println(o.getClass());
        System.out.println(o);
    }
}
