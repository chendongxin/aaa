package com.hqjy.mustang.transfer.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hqjy.mustang.transfer.export", "com.hqjy.mustang.common"})
public class TransferExportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferExportApplication.class, args);
    }
}
