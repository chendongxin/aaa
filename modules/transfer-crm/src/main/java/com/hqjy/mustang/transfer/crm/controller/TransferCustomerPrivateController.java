package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

//    @ApiOperation(value = "分页查询-私海客户列表", notes = "请求参数：\n" +
//            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
//            "高级查询参数（RequestBody数据格式接收）：[beginCreateTime:开始创建时间],[endCreateTime:结束创建时间],[beginAllotTime:开始分配时间],[endAllotTime:结束分配时间]\n" +
//            "[deptId:部门id],[name:姓名],[phone:手机],[userName:归属人]\n" +
//            "返回参数：【当前页:currPage】,【当前页的数量:size】,【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
//            "【客户编号：customerId】,【姓名:name】,【地址:address】,【咨询次数:consult】,【手机:phone】,【商机来源（0：系统 ,1：官网,2：客服,3：代理网）:source】\n" +
//            "【创建人:createName】,【创建时间:createTime】,【商机状态(-1：无效 0：潜在 1：预约 2：成交)):status】\n" +
//            "【性别(-1：女 0：未知 1：男):sex】,【客服账号:xnId】,【来源url:url】,【备注:memo】,【分配时间：allotTime】\n" +
//            "示例：\n" +
//            "{\n" +
//            "  \"msg\": \"成功\",\n" +
//            "  \"result\": {\n" +
//            "    \"currPage\": 1,\n" +
//            "    \"endRow\": 1,\n" +
//            "    \"list\": [\n" +
//            "      {\n" +
//            "        \"address\": \"ccccccccc\",\n" +
//            "        \"consult\": 0,\n" +
//            "        \"createId\": 1,\n" +
//            "        \"createName\": \"admin\",\n" +
//            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
//            "        \"customerId\": 1,\n" +
//            "        \"memo\": \"的说法是否是\",\n" +
//            "        \"name\": \"灵儿\",\n" +
//            "        \"ncId\": \"cxv\",\n" +
//            "        \"phone\": \"13631153950\",\n" +
//            "        \"sex\": 0,\n" +
//            "        \"source\": 0,\n" +
//            "        \"status\": 0,\n" +
//            "        \"url\": \"yyyyyyyyyyyyyyyyy\"\n" +
//            "      }\n" +
//            "    ],\n" +
//            "    \"pageSize\": 10,\n" +
//            "    \"size\": 1,\n" +
//            "    \"startRow\": 1,\n" +
//            "    \"totalCount\": 1,\n" +
//            "    \"totalPage\": 1\n" +
//            "  },\n" +
//            "  \"code\": 0\n" +
//            "}")
//    @RequestMapping(value = "/listPage",method = {RequestMethod.POST,RequestMethod.GET})
//    @RequiresPermissions("biz:private:list")
//    public R list(@RequestParam HashMap<String, Object> pageParam,
//                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
//        PageInfo<TransferCustomerEntity> deptPageInfo = new PageInfo<>(transferCustomerService.findPrivatePage(PageQuery.build(pageParam, queryParam)));
//        return R.ok(deptPageInfo);
//    }
}
