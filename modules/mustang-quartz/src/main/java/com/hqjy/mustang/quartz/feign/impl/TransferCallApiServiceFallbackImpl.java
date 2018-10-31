package com.hqjy.mustang.quartz.feign.impl;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.quartz.feign.TrasferCallApiService;
import com.hqjy.mustang.quartz.feign.TrasferResumeApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class TransferCallApiServiceFallbackImpl implements TrasferCallApiService {


    @Override
    public R callOut(Long timestamp) {
        log.error("通话记录调用异常");
        return R.error();
    }
}
