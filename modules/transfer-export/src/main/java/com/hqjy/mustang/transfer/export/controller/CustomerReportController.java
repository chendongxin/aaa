package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.CustomerUpDTO;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import com.hqjy.mustang.transfer.export.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author gmm
 * @date create on 2018/10/11
 * @apiNote 客户报表控制层
 */

@Api(tags = "客户报表导入导出", description = "CustomerReportController")
@RestController
@RequestMapping("/report/customer")
public class CustomerReportController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "导入客户列表", notes = "请求参数方式：multipart/form-data,支持多参数类型请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proId", paramType = "query", value = "赛道ID", dataType = "Long"),
            @ApiImplicitParam(name = "companyId", paramType = "query", value = "推广公司ID", dataType = "Long"),
            @ApiImplicitParam(name = "deptId", paramType = "query", value = "部门ID", dataType = "Long"),
            @ApiImplicitParam(name = "firstUserId", paramType = "query", value = "首次跟进人ID", dataType = "Long"),
            @ApiImplicitParam(name = "sourceId", paramType = "query", value = "来源平台ID", dataType = "Long"),
            @ApiImplicitParam(name = "getWay", paramType = "query", value = "获取方式", dataType = "Byte"),
            @ApiImplicitParam(name = "notAllot", paramType = "query", value = "是否不分配：true-不分配，false-自动分配", dataType = "boolean")
    })
    @PostMapping("/importCustomer")
//    @RequiresPermissions("biz:customer:import")
    @SysLog("客户列表导入")
    public R importCustomer(@RequestParam("file") MultipartFile file, @ModelAttribute CustomerUpDTO dto) {
        return customerService.importCustomer(file, dto);
    }

    @ApiOperation(value = "导出客户列表", notes = "请求参数格式:见swagger的model:客户报表导出参数模型")
    @PostMapping("/exportCustomer")
//    @RequiresPermissions("biz:customer:export")
    @SysLog("客户列表导出")
    public R exportCustomer(@RequestBody CustomerExportQueryParams query) {
        return R.result(customerService.exportCustomer(query));
    }
}
