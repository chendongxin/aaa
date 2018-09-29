package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerInvalidService;
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
 * @ date create in 2018年9月21日15:30:48
 */
@Api(tags = "客户管理-无效", description = "TransferCustomerInvalidController")
@RestController
@RequestMapping("/customer/invalid")
public class TransferCustomerInvalidController {

    @Autowired
    private TransferCustomerInvalidService transferCustomerInvalidService;

    @ApiOperation(value = "无效操作", notes = "请求参数：\n" +
            "参数说明：[customerId:客户ID], [memo: 备注], [type:无效类型]\n" +
            "示例：\n" +
            "{\n" +
            "    \"customerId\": 0,\n" +
            "    \"memo\": \"string\",\n" +
            "    \"type\": 0\n" +
            "}")
    @PostMapping("/setCustomerInvalid")
//    @RequiresPermissions("biz:invalid:setInvalid")
    @SysLog("无效操作")
    public R setCustomerInvalid(@RequestBody TransferCustomerDTO dto) {
        return transferCustomerInvalidService.setCustomerInvalid(dto);
    }
}
