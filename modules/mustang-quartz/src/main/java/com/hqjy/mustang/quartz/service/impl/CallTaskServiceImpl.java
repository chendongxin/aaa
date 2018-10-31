package com.hqjy.mustang.quartz.service.impl;

import com.hqjy.mustang.quartz.feign.TrasferCallApiService;
import com.hqjy.mustang.quartz.feign.TrasferResumeApiService;
import com.hqjy.mustang.quartz.service.CallTaskService;
import com.hqjy.mustang.quartz.service.ResumerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;


@Service
@Slf4j
public class CallTaskServiceImpl implements CallTaskService {

    private final TrasferCallApiService trasferCallApiService;

    @Autowired
    public CallTaskServiceImpl(TrasferCallApiService transferCallApiServiceFallbackImpl) {
        this.trasferCallApiService = transferCallApiServiceFallbackImpl;
    }

    @Override
    public void callStart() {
        log.info("开始同步通话记录");
       trasferCallApiService.callOut(LocalDateTime.now().minus(30, ChronoUnit.DAYS).toEpochSecond(ZoneOffset.of("+8")));
        log.info("结束同步通话记录");
    }
}


