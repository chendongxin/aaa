package com.hqjy.mustang.quartz.task;

import com.hqjy.mustang.quartz.service.CallTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("trasferCallTask")
public class TrasferCallTask {

    @Autowired
    private final CallTaskService callTaskService;

    public TrasferCallTask(CallTaskService callTaskService) {
        this.callTaskService = callTaskService;
    }

    public void callStart() {
        callTaskService.callStart();
    }
}
