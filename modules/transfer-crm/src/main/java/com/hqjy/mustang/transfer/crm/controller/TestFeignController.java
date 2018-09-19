package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.feign.TestFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyq
 * @date create on 2018/9/18
 * @apiNote
 */
@Api(tags = "测试声明式调用接口", description = "TestFeignController")
@RestController
@RequestMapping("/test")
public class TestFeignController {

    private TestFeignClient testFeignClient;

    @Autowired
    public void setUserFeignClient(TestFeignClient testFeignClient) {
        this.testFeignClient = testFeignClient;
    }

    @ApiOperation(value = "test", notes = "test")
    @GetMapping(value = "/test")
    public R getUser() {
        return testFeignClient.test();
    }
}
