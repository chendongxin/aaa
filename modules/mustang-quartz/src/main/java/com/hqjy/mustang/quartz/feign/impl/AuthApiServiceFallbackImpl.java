package com.hqjy.mustang.quartz.feign.impl;

import com.hqjy.mustang.common.web.model.AuthCheckResult;
import com.hqjy.mustang.quartz.feign.AuthApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class AuthApiServiceFallbackImpl implements AuthApiService {

    @Override
    public AuthCheckResult checkToken(Long userId, String jti) {
        log.error("调用{}异常:{},{}", "checkToken", userId, jti);
        return null;
    }
}
