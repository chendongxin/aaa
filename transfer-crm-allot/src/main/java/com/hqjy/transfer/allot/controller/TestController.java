package com.hqjy.transfer.allot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hqjy.transfer.api.export.service.TestService;
import com.hqjy.transfer.common.base.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/4 14:46
 */
@RestController
@RequestMapping
public class TestController {
    @Reference
    private TestService testService;

    @GetMapping("/{name}")
    public R test(@PathVariable("name") String name) {
        return R.ok(testService.findByName(name));
    }
}
