package com.hqjy.mustang.transfer.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MustangApplication 启动类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.hqjy.mustang.transfer.crm", "com.hqjy.mustang.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class TransferCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferCrmApplication.class, args);
    }
}
