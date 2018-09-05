package com.hqjy.transfer.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hqjy.transfer.export", "com.hqjy.transfer.common"})
public class ExportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExportApplication.class, args);
    }
}
