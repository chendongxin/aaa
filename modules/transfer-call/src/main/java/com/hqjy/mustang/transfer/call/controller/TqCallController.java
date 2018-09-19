package com.hqjy.mustang.transfer.call.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:42
 */
@Api(tags = "TQ通话中心", description = "TqCallController")
@RestController
@RequestMapping("/callCenter")
public class TqCallController {

    @Autowired
    private TqCallService tqCallService;

    @ApiOperation(value = "点击外呼", notes = "需要路径参数： 【customerId：客户编号，phone：外呼号码】")
    @PostMapping("/callOut/{customerId}/{phone}")
    public R callOut(@PathVariable("customerId") Long customerId, @PathVariable("phone") String phone) {
        if (tqCallService.callOut(customerId, phone)) {
            return R.ok();
        } else {
            return R.error("外呼失败");
        }
    }
}
