package com.hqjy.transfer.allot.api;

import com.hqjy.transfer.common.base.constant.Constant;
import com.hqjy.transfer.common.model.allot.CustomerInfo;
import org.apache.commons.lang3.RandomUtils;
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
    public CustomerInfo reset() {
        CustomerInfo customerInfoDTO = new CustomerInfo();
        customerInfoDTO.setCustomerId(RandomUtils.nextLong());
        customerInfoDTO.setName("测试客户");
        customerInfoDTO.setPhone("1388888888");
        return customerInfoDTO;
    }
}
