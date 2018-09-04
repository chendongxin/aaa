package com.hqjy.transfer.export.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hqjy.transfer.api.export.model.TestEntity;
import com.hqjy.transfer.api.export.service.TestService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * @author : heshuangshuang
 * @date : 2018/9/4 14:43
 */
@Service(interfaceClass = TestService.class)
@Component
public class TestServiceImpl implements TestService {


    @Override
    public TestEntity findByName(String name) {

        TestEntity testEntity = new TestEntity();
        testEntity.setAge(RandomUtils.nextInt());
        testEntity.setSex("男");
        testEntity.setName(name);
        return testEntity;
    }
}
