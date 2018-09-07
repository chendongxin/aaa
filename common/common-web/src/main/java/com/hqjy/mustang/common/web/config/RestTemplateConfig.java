package com.hqjy.mustang.common.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 17:39
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(1800000);
        httpRequestFactory.setConnectTimeout(1800000);
        httpRequestFactory.setReadTimeout(1800000);
        return new RestTemplate(httpRequestFactory);
    }
}
