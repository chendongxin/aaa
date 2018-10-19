package com.hqjy.mustang.quartz.service.impl;

import com.hqjy.mustang.quartz.feign.TrasferResumeApiService;
import com.hqjy.mustang.quartz.feign.impl.TransferResumeApiServiceFallbackImpl;
import com.hqjy.mustang.quartz.service.ResumerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/10/16 14:31
 */
@Service
@Slf4j
public class ResumerTaskServiceImpl implements ResumerTaskService {

    private final TrasferResumeApiService trasferResumeApiService;

    @Autowired
    public ResumerTaskServiceImpl(TrasferResumeApiService transferResumeApiServiceFallbackImpl) {
        this.trasferResumeApiService = transferResumeApiServiceFallbackImpl;
    }

    /**
     * 简历抓取任务，指定时间之前
     */
    @Override
    public void resumeStartBefor(String hourStr) {
        boolean result = trasferResumeApiService.start(Integer.valueOf(hourStr));
        if (result) {
            log.info("简历抓取任务执行成功，参数为：" + hourStr);
        } else {
            log.error("简历抓取任务执行失败，参数为：" + hourStr);
        }
    }

    /**
     * 简历抓取任务
     */
    @Override
    public void resumeStart() {
        boolean result = trasferResumeApiService.start();
        if (result) {
            log.info("简历抓取任务执行成功");
        } else {
            log.error("简历抓取任务执行失败");
        }
    }
}
