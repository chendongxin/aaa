package com.hqjy.mustang.allot.feign.impl;

import com.hqjy.mustang.allot.feign.TransferKeywordApiService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 17:51
 */
@Slf4j
public class TransferKeywordApiServiceFallbackImpl implements TransferKeywordApiService {
    /**
     * 根据职位信息，遍历出关键字
     */
    @Override
    public String getKeyword(String info) {
        log.error("调用{}异常:{},info：{}", "根据职位信息，遍历出关键字", info);
        return null;
    }
}
