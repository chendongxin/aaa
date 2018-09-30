package com.hqjy.mustang.transfer.sms.fegin.fallback;

import com.hqjy.mustang.common.model.crm.TransferCustomerInfo;
import com.hqjy.mustang.transfer.sms.fegin.TransferCustomerApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:56
 */
@Service
@Slf4j
public class TransferCustomerApiServiceFallbackImpl implements TransferCustomerApiService {

    /**
     * 根据电话和部门，查询一个客户信息
     */
    @Override
    public TransferCustomerInfo findByPhoneAndDeptId(Long deptId, String phone) {
        log.error("调用{} 异常；deptId:{}，phone:{}", "根据电话和部门，查询一个客户信息", deptId, phone);
        return null;
    }
}
