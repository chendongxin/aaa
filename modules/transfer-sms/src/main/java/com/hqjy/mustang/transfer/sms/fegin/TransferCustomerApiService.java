package com.hqjy.mustang.transfer.sms.fegin;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.crm.TransferCustomerInfo;
import com.hqjy.mustang.transfer.sms.fegin.fallback.TransferCustomerApiServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 登陆逻辑查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "transfer-crm", fallback = TransferCustomerApiServiceFallbackImpl.class)
public interface TransferCustomerApiService {

    /**
     * 根据电话和部门，查询一个客户信息
     */
    @GetMapping(value = Constant.API_PATH + "/customer/findByPhoneAndDeptId/{deptId}/{phone}")
    TransferCustomerInfo findByPhoneAndDeptId(@PathVariable("deptId") Long deptId, @PathVariable("phone") String phone);
}
