package com.hqjy.mustang.admin.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author gmm
 * @date 2018年11月3日16:29:46
 */
@FeignClient(name = "transfer-crm")
public interface TransferCustomerApiService {

    /**
     * 根据用户id更新用户非成交商机到公海
     */
    @GetMapping(Constant.API_PATH + "customer/getCustomer/{userId}/{sign}")
    Integer updateUserTransferToPublic(@PathVariable("userId") Long userId, @PathVariable("sign") Boolean sign);
}
