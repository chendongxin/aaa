package com.hqjy.mustang.allot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hqjy.mustang.allot", "com.hqjy.mustang.common"})
public class AllotApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllotApplication.class, args);
    }
}