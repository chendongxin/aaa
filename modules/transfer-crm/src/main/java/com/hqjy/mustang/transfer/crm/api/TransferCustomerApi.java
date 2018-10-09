package com.hqjy.mustang.transfer.crm.api;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.crm.TransferCustomerInfo;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author : heshuangshuang
 * @date : 2018/9/30 16:55
 */
@RestController
@RequestMapping(Constant.API_PATH + "/customer")
@Slf4j
public class TransferCustomerApi {

    @Autowired
    private TransferCustomerService transferCustomerService;

    /**
     * 根据电话和部门，查询一个客户信息
     */
    @GetMapping("/findByPhoneAndDeptId/{deptId}/{phone}")
    public TransferCustomerInfo findByPhoneAndDeptId(@PathVariable("deptId") Long deptId, @PathVariable("phone") String phone) {
        return Optional.ofNullable(transferCustomerService.findByPhoneAndDeptId(deptId, phone)).map(s -> PojoConvertUtil.convert(s, TransferCustomerInfo.class)).orElse(null);
    }
}
