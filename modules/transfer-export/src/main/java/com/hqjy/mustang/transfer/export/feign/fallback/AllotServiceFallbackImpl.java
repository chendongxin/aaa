package com.hqjy.mustang.transfer.export.feign.fallback;

import com.hqjy.mustang.common.model.allot.CustomerInfo;
import com.hqjy.mustang.transfer.export.feign.AllotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务的fallback
 */
@Service
@Slf4j
public class AllotServiceFallbackImpl implements AllotService {

    @Override
    public CustomerInfo reset() {
        log.error("调用{}异常:{}", "get");
        return null;
    }

}
