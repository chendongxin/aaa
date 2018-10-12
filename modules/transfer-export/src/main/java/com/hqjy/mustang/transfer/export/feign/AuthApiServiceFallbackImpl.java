package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.web.model.AuthCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author :xyq
 * @date :  2018年10月11日09:17:38
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
