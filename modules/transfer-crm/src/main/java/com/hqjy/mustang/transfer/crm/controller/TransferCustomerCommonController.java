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
 * @ date create in 2018年9月28日11:31:03
 */
@Api(tags = "客户管理-公海", description = "TransferCustomerCommonController")
@RestController
@RequestMapping("/customer/common")
public class TransferCustomerCommonController {

    private TransferCustomerService transferCustomerService;

    @Autowired
    public void setTransferCustomerService(TransferCustomerService transferCustomerService) {
        this.transferCustomerService = transferCustomerService;
    }

    @ApiOperation(value = "分页查询-公海客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[createName:创建人姓名],[姓名:name],[手机:phone],[部门：deptId]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【客户编号:customerId】,【NC客户编号:ncId】,【赛道编号:proId】,【赛道名称:proName】,【来源平台ID:sourceId】,【来源平台名称:sourceName】\n" +
            "【推广公司ID:companyId】,【推广公司名称:companyName】,【姓名:name】,【商机状态1-(失败)有效, 2-(失败)无效, 3-预约, 4-成交)):status】\n" +
            "【推广方式(1-主动获取，2-被动获取):getWay】,【当前跟进人ID:userId】,【当前跟进人姓名:userName】,【当前跟进人部门ID:deptId】\n" +
            "【当前跟进人部门名称:deptName】,【首次跟进人ID:firstUserId】,【首次跟进人姓名:firstUserName】,【最后跟进人ID:lastUserId】\n" +
            "【最后跟进人姓名:lastUserName】,【推广用户ID:genUserId】,【推广用户姓名:genUserName】,【手机号码:phone】,【微信:weChat】\n" +
            "【QQ:qq】,【座机:landLine】,【创建人ID:createUserId】,【创建人姓名:createUserName】,【创建时间:createTime】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"customerId\": 1,\n" +
            "        \"ncId\": 1,\n" +
            "        \"proId\": 1,\n" +
            "        \"proName\": \"IT赛道\",\n" +
            "        \"sourceId\": 1,\n" +
            "        \"sourceName\": \"智联\",\n" +
            "        \"companyId\": 1,\n" +
            "        \"companyName\": \"广州海度网络科技有限公司\",\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"status\": 1,\n" +
            "        \"getWay\": 1,\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"王超\",\n" +
            "        \"deptId\": 1,\n" +
            "        \"deptName\": \"益阳校区\",\n" +
            "        \"firstUserId\": 1,\n" +
            "        \"firstUserName\": \"丫丫\",\n" +
            "        \"lastUserId\": 1,\n" +
            "        \"lastUserName\": \"美丽\",\n" +
            "        \"genUserId\": 1,\n" +
            "        \"genUserName\": \"翰林\",\n" +
            "        \"phone\": \"18578548523\",\n" +
            "        \"weChat\": \"18578548523\",\n" +
            "        \"qq\": \"18578548523\",\n" +
            "        \"landLine\": \"6541236\",\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"王超群\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
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
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
//    @RequiresPermissions("biz:common:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerEntity> commonPageInfo = new PageInfo<>(transferCustomerService.findCommonPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(commonPageInfo);
    }

    @ApiOperation(value = "领取客户", notes = "请求参数：\n" +
            "例：" +
            "[\n" +
            "  32,33\n" +
            "]")
    @PostMapping("/receiveTransferCustomer")
//    @RequiresPermissions("biz:common:receive")
    @SysLog("公海领取商机")
    public R receiveBizCustomer(@RequestBody List<Long> customerId) {

        return transferCustomerService.receiveTransferCustomer(customerId);
    }
}
