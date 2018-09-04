package com.hqjy.transfer.export;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.hqjy.transfer.common.base.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableDubboConfiguration
public class ExportApplication {
    /**
     * 使用jar方式打包的启动方式
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Constant.getDriver(0));

        SpringApplication.run(ExportApplication.class, args).registerShutdownHook();
        countDownLatch.await();
    }
}
