package com.hqjy.mustang.transfer.crawler.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import com.hqjy.mustang.transfer.crawler.service.TrasferResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

/**
 * @author : heshuangshuang
 * @date : 2018/9/11 9:38
 */
@RestController
@RequestMapping(Constant.API_PATH + "/crawler")
public class TrasferResumeApi {

    @Autowired
    private TrasferResumeService trasferResumeService;

    /**
     * 提供给定时任务调用的接口
     */
    @GetMapping("/start")
    public boolean start(@RequestParam(value = "hour", required = false) Integer hour) {
        // 指定时间
        Date beforeDate = Optional.ofNullable(hour)
                .map(h -> Date.from(LocalDateTime.now().minus(hour, ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant()))
                .orElseGet(() -> {
                    // 没有指定时间，查询数据库中最后一条记录
                    TransferResumeEntity lastResume = trasferResumeService.findLast();
                    if (lastResume != null) {
                        // 获取最后一条记录的发送时间
                        return lastResume.getSendTime();
                    }
                    // 都不存在，获取24个小时之前的
                    return Date.from(LocalDateTime.now().minus(24, ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant());
                });
        return trasferResumeService.start(beforeDate);
    }
}
