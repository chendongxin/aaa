package com.hqjy.mustang.transfer.call.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:42
 */
@RestController
@RequestMapping("/tq")
public class TqCallController {
    @Autowired
    private TqCallService tqCallService;

    @PostMapping("/callOut/{phone}")
    public R callOut(@PathVariable("phone") String phone) {
        if (tqCallService.callOut(phone)) {
            return R.ok();
        } else {
            return R.error("外呼失败");
        }
    }

}
