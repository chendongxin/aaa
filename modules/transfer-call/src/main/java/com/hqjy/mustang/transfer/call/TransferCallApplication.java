package com.hqjy.mustang.transfer.call;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * MustangApplication 启动类
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/03/22 10:54
 */
@ComponentScan(basePackages = {"com.hqjy.mustang.transfer.call", "com.hqjy.mustang.common"})
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class TransferCallApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferCallApplication.class, args);
    }
}
