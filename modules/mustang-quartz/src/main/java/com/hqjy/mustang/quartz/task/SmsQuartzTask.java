package com.hqjy.mustang.quartz.task;

import com.hqjy.mustang.quartz.feign.SmsQuartzFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Autor: hutao
 * Date: 2018-10-30-15:49
 */
@Service("SmsQuartzTask")
@Slf4j
public class SmsQuartzTask {
    @Autowired
    private final SmsQuartzFeign smsQuartzFeign;
    public SmsQuartzTask(SmsQuartzFeign smsQuartzFeign){
        this.smsQuartzFeign = smsQuartzFeign;
    }

    public void smsReportQuartz(){
        smsQuartzFeign.status();
    }
}
