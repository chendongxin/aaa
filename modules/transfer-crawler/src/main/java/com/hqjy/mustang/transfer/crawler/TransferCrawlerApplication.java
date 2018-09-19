package com.hqjy.mustang.transfer.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * MustangApplication 启动类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hqjy.mustang.transfer.crawler", "com.hqjy.mustang.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class TransferCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferCrawlerApplication.class, args);
    }
}
