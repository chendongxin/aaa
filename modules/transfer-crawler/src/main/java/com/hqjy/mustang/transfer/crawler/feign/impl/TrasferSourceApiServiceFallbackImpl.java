package com.hqjy.mustang.transfer.crawler.feign.impl;

import com.hqjy.mustang.common.model.crm.TransferSourceInfo;
import com.hqjy.mustang.transfer.crawler.feign.TrasferSourceApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class TrasferSourceApiServiceFallbackImpl implements TrasferSourceApiService {

    /**
     * 根据邮箱域名查询来源配置
     */
    @Override
    public TransferSourceInfo findByEmailDomain(String email) {
        log.error("调用 邮箱域名查询来源 失败 ，未知来源 {}", email);
        return null;
    }
}
