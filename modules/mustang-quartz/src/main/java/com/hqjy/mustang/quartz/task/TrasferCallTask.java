package com.hqjy.mustang.quartz.task;

import com.hqjy.mustang.quartz.service.CallTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("callTask")
public class TrasferCallTask {

    @Autowired
    private final CallTaskService callTaskService;

    public TrasferCallTask(CallTaskService callTaskService) {
        this.callTaskService = callTaskService;
    }

    public void callStartStr(String hourStr) {
        callTaskService.callStartStr(hourStr);
    }
    public void callStart() {
        callTaskService.callStart();
    }
}
