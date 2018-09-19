package com.hqjy.mustang.transfer.call.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 10:17
 */
@RestController
@RequestMapping(Constant.API_PATH)
@Slf4j
public class TqCallApi {

    @Autowired
    private TqCallService tqCallService;

    /**
     * 执行同步通话记录，参数为开始同步的时间
     */
    @PostMapping("/syncRecord/{timestamp}")
    public R callOut(@PathVariable("timestamp") long timestamp) {
        tqCallService.syncRecord(timestamp);
        return R.ok();
    }

}
