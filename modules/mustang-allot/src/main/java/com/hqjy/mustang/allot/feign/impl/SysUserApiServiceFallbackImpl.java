package com.hqjy.mustang.allot.feign.impl;

import com.hqjy.mustang.allot.feign.SysUserApiService;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class SysUserApiServiceFallbackImpl implements SysUserApiService {

    /**
     * 根据用户Id查询用户
     */
    @Override
    public SysUserInfo findOne(Long userId) {
        log.error("调用{}异常:{},userId：{}", "根据用户Id查询用户", userId);
        return null;
    }

}
