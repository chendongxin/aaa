package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author gmm
 * @ description
 * @ date create in 2018年9月29日14:21:03
 */
@Api(tags = "客户管理-私海", description = "TransferCustomerPrivateController")
@RestController
@RequestMapping("/customer/private")
public class TransferCustomerPrivateController extends AbstractMethodError {

    @Autowired
    private TransferCustomerService transferCustomerService;

    @ApiOperation(value = "分页查询-私海客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[beginCreateTime:开始创建时间],[endCreateTime:结束创建时间]\n" +
            "[deptId:部门id],[name:姓名],[phone:手机],[userId:归属人id]\n" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】,【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【客户编号：customerId】,【姓名:name】,【归属人编号：userId】,【归属人姓名:userName】,【部门编号：deptId】,【部门名称:deptName】,【手机:phone】\n" +
            "【创建人编号:createUserId】,【创建人名称:createUserName】,【创建时间:createTime】,【商机状态(0-潜在，1-(失败)有效，2-(失败)无效，3-预约，4-成交)):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"customerId\": 1,\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"admin\",\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"admin\",\n" +
            "        \"deptId\": 1,\n" +
            "        \"deptName\": \"admin\",\n" +
            "        \"phone\": \"13631153950\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
            "        \"status\": 0,\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageSize\": 10,\n" +
            "    \"size\": 1,\n" +
            "    \"startRow\": 1,\n" +
            "    \"totalCount\": 1,\n" +
            "    \"totalPage\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage",method = {RequestMethod.POST,RequestMethod.GET})
//    @RequiresPermissions("biz:private:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerEntity> deptPageInfo = new PageInfo<>(transferCustomerService.findPrivatePage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    @ApiOperation(value = "私海客户退回公海", notes = "请求参数：\n" +
            "例：" +
            "[\n" +
            "  32,33\n" +
            "]")
    @PostMapping("/returnToCommon")
//    @RequiresPermissions("biz:private:returnToCommon")
    @SysLog("私海客户退回公海")
    public R returnToCommon(@RequestBody List<Long> customerId) {
        return transferCustomerService.returnToCommon(customerId);
    }
}
