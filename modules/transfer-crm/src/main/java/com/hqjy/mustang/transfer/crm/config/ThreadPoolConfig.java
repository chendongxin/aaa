package com.hqjy.mustang.transfer.crm.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean("receiveExecutor")
    public ThreadPoolExecutor receiveExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("receive-pool-%d").build();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(
                availableProcessors,
                availableProcessors * 2,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
