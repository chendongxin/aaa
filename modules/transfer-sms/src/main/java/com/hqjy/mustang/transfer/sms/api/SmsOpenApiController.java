package com.hqjy.mustang.transfer.sms.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.sms.service.TransferSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : heshuangshuang
 * @date : 2018/9/27 17:53
 */
@RequestMapping(Constant.API_OPEN_PATH)
@RestController
@Slf4j
public class SmsOpenApiController {
    @Autowired
    private TransferSmsService transferSmsService;

    /**
     * 短信发送回调
     */
    @PostMapping("/status")
    public R status() {
        transferSmsService.smsReport();
        return R.ok();
    }

    /**
     * sms短信回复回调
     */
    @PostMapping("/reply")
    public R reply(@RequestBody(required = false) String body) {
        transferSmsService.smsReply(body);
        return R.ok();
    }

}
