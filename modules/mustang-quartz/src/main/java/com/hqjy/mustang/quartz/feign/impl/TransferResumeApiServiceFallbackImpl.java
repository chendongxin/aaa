package com.hqjy.mustang.quartz.feign.impl;

import com.hqjy.mustang.quartz.feign.TrasferResumeApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class TransferResumeApiServiceFallbackImpl implements TrasferResumeApiService {
    @Override
    public boolean start() {
        log.error("调用简历抓取任务异常");
        return false;
    }

    @Override
    public boolean start(Integer hour) {
        log.error("调用简历抓取任务异常，参数hour:{}", hour);
        return false;
    }
}
