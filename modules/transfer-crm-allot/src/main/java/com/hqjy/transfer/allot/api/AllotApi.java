package com.hqjy.transfer.allot.api;

import com.hqjy.transfer.common.base.constant.Constant;
import com.hqjy.transfer.common.redis.utils.RedisKeys;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : heshuangshuang
 * @date : 2018/9/5 14:56
 */
@RestController
@RequestMapping(Constant.API_PATH)
public class AllotApi {

    @GetMapping("/reset")
    public boolean reset() {
        return true;
    }
}
