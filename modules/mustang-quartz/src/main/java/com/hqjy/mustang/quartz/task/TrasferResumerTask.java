package com.hqjy.mustang.quartz.task;

import com.hqjy.mustang.quartz.service.ResumerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Slf4j
@Service("trasferResumerTask")
public class TrasferResumerTask {

    @Autowired
    private final ResumerTaskService resumerTaskService;

    public TrasferResumerTask(ResumerTaskService resumerTaskService) {
        this.resumerTaskService = resumerTaskService;
    }

    /**
     * 简历抓取任务，指定时间之前
     */
    public void resumeStartBefor(String hourStr) {
        resumerTaskService.resumeStartBefor(hourStr);
    }

    /**
     * 简历抓取任务
     */
    public void resumeStart() {
        resumerTaskService.resumeStart();
    }
}
