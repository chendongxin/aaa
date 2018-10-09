package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDealEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.hqjy.mustang.common.base.utils.PageQuery.build;

/**
 * @author gmm
 * @ description
 * @ date create in 2018年9月28日14:21:03
 */
@Api(tags = "客户管理-成交", description = "TransferCustomerDealController")
@RestController
@RequestMapping("/transfer/deal")
public class TransferCustomerDealController {


    private TransferCustomerDealService transferCustomerDealService;

    @Autowired
    public void setTransferCustomerDealService(TransferCustomerDealService transferCustomerDealService) {
        this.transferCustomerDealService = transferCustomerDealService;
    }

    @ApiOperation(value = "分页查询-成交客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[客户姓名:name],[客户电话:phone]," +
            "[beginCreateTime：创建开始时间],[beginDealTime：成交开始时间]\n" +
            "[endCreateTime：创建结束时间],[endDealTime：成交结束时间]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【客户姓名:name】,【客户电话:phone】,【userName:成交人】,【创建时间:bCreateTime】,【成交时间:createTime】\n" +
            "示例：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"userName\": \"王俊凯\",\n" +
            "                \"createTime\": \"2018-06-14 11:43:17\",\n" +
            "                \"bCreateTime\": \"2018-06-14 11:43:17\",\n" +
            "                \"customerId\": 22,\n" +
            "                \"name\": \" 姣姣人\",\n" +
            "                \"phone\": \"13631153888\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 10,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 1,\n" +
            "        \"totalPage\": 1\n" +
            "    }\n" +
            "}"
    )
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    //@RequiresPermissions("biz:deal:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerDealEntity> deptPageInfo = new PageInfo<>(transferCustomerDealService.findPage(build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

}
