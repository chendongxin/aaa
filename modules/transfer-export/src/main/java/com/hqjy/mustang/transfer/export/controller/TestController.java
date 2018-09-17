package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.feign.AllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/5 15:35
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private AllotService allotService;

    @GetMapping("/reset")
    public R reset() {
        System.out.println("test");
        return R.ok(allotService.reset());
    }

}
