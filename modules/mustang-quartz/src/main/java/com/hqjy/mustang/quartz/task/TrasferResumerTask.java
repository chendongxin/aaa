package com.hqjy.mustang.quartz.task;

import com.hqjy.mustang.quartz.feign.impl.TransferResumeApiServiceFallbackImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Service("trasferResumerTask")
@Slf4j
public class TrasferResumerTask {

    private final TransferResumeApiServiceFallbackImpl trasferResumeApiService;

    @Autowired
    public TrasferResumerTask(TransferResumeApiServiceFallbackImpl trasferResumeApiService) {
        this.trasferResumeApiService = trasferResumeApiService;
    }

    /**
     * 简历抓取任务，指定时间之前
     */
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
    public void resumeStart() {
        boolean result = trasferResumeApiService.start();
        if (result) {
            log.info("简历抓取任务执行成功");
        } else {
            log.error("简历抓取任务执行失败");
        }
    }
}
