package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmm
 * @ description
 * @ date create in 2018年10月9日15:30:48
 */
@Api(tags = "客户管理-联系方式", description = "TransferCustomerContactController")
@RestController
@RequestMapping("/customer/contact")
public class TransferCustomerContactController {

    private TransferCustomerContactService transferCustomerContactService;

    @Autowired
    public void setTransferCustomerContactService(TransferCustomerContactService transferCustomerContactService) {
        this.transferCustomerContactService = transferCustomerContactService;
    }

    @ApiOperation(value = "新增客户联系方式", notes = "请求参数：\n" +
            "[customerId客户主键，detail联系方式详情,type联系方式类型]\n" +
            "请求示例：\n" +
            "{\n" +
            "\n" +
            "  \"customerId\": 1,\n" +
            "  \"detail\": \"ugdsgks@136.com\",\n" +
            "  \"type\": 3\n" +
            "}")
    @SysLog("新增客户联系方式")
    @PostMapping("/save")
    public R save(@RequestBody TransferCustomerContactEntity entity) {
        try {
            return transferCustomerContactService.save(entity) > 0 ? R.ok() : R.error("此号码对应的客户已经存在");
        } catch (Exception e) {
            return R.error("客户联系方式新增异常：" + e.getMessage());
        }
    }
}
