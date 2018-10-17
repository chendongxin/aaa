package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import com.hqjy.mustang.transfer.export.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author gmm
 * @date create on 2018/10/11
 * @apiNote 客户报表控制层
 */

@Api(tags = "客户报表导出", description = "CustomerReportController")
@RestController
@RequestMapping("/report/customer")
public class CustomerReportController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "导出客户列表", notes = "请求参数格式:见swagger的model:客户报表导出参数模型")
    @PostMapping("/exportCustomer")
//    @RequiresPermissions("biz:customer:export")
    @SysLog("客户列表导出")
    public R exportCustomer(@RequestBody CustomerExportQueryParams query) {
        return R.result(customerService.exportCustomer(query));
    }
}
