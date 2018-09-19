package com.hqjy.mustang.allot.feign.impl;

import com.hqjy.mustang.allot.feign.SysConfigApiService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class SysConfigApiServiceFallbackImpl implements SysConfigApiService {

    /**
     * 根据配置编码获取系统配置信息
     */
    @Override
    public String getConfig(String code) {
        log.error("调用{}异常:{},code：{}", "根据配置编码获取系统配置信息", code);
        return null;
    }

}
