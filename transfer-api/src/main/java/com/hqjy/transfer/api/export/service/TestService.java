package com.hqjy.transfer.api.export.service;

import com.hqjy.transfer.api.export.model.TestEntity;

/**
 * dubbo 服务接口测试
 *
 * @author : heshuangshuang
 * @date : 2018/9/4 14:13
 */
public interface TestService {
    TestEntity findByName(String name);
}
