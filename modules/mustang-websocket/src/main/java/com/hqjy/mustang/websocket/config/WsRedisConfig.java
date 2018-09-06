package com.hqjy.mustang.websocket.config;

import com.hqjy.mustang.websocket.listener.WsMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2017/12/3 21:55
 */
@Component
public class WsRedisConfig {
    /**
     * websocket监听redis主题的名称,默认 websocket
     */
    @Value("${spring.redis.ws-topic-name}")
    private String wsTopicName;

    @Autowired
    private WsMessageListener wsMessageListener;

    /**
     * Redis消息侦听器提供异步行为的容器。 处理侦听，转换和消息分派的低级细节
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //注册消息监听器
        container.addMessageListener(wsMessageListener, new PatternTopic(wsTopicName));
        return container;
    }
}
