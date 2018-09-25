package com.hqjy.mustang.transfer.call.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/25 22:07
 */
@Configuration
public class RestTemplateConfig {


    @Bean("openRestTemplate")
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }


    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //ms
        factory.setReadTimeout(120000);
        //ms
        factory.setConnectTimeout(120000);
        return factory;
    }
}

