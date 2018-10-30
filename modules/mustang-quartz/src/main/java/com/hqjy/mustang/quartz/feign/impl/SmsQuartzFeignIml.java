package com.hqjy.mustang.quartz.feign.impl;

import com.hqjy.mustang.quartz.feign.SmsQuartzFeign;
import com.hqjy.mustang.common.base.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Autor: hutao
 * Date: 2018-10-30-15:09
 */
@Service
@Slf4j
public class SmsQuartzFeignIml implements SmsQuartzFeign {
    @Override
    public R status(){
        log.error("调用短信通知接口异常");
        return R.ok();
    }

}
