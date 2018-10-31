package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerInvalidEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerInvalidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.hqjy.mustang.common.base.utils.PageQuery.build;

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

    @ApiOperation(value = "分页查询-无效客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[createUserName:创建人姓名],[姓名:name],[手机:phone]\n" +
            "[开始创建时间：beginCreateTime],[结束创建时间：endCreateTime],[开始无效时间：beginInvalidTime],[结束无效时间：endInvalidTime]\n" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】,【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【姓名:name】,【手机:phone】,【无效创建时间:createTime】,【失败状态(1-(失败)有效，2-（失败）无效):status】,【type(见数据字典：失败(有效)类型（VALID_TYPE）和失败(无效)类型（INVALID）):失败原因】\n" +
            "【创建人id:createUserId】,【创建人名称:createUserName】,【客户id:customerId】,【失败操作说明:memo】,【创建时间:transferCreateTime】,【无效时间:createTime】\n" +
            "响应示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 2,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"name\": \"灵儿11112\",\n" +
            "        \"phone\": \"13631153950\",\n" +
            "        \"createUserId\": 56,\n" +
            "        \"createUserName\": \"王俊凯\",\n" +
            "        \"createTime\": \"2018-06-14 11:58:50\",\n" +
            "        \"transferCreateTime\": \"2018-06-14 11:58:50\",\n" +
            "        \"status\": 0,\n" +
            "        \"type\": 1\n" +
            "        \"customerId\": 1\n" +
            "        \"memo\": \"时间不充足\",\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageSize\": 10,\n" +
            "    \"size\": 2,\n" +
            "    \"startRow\": 1,\n" +
            "    \"totalCount\": 2,\n" +
            "    \"totalPage\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @RequestMapping(value = "/listPage",method = {RequestMethod.POST,RequestMethod.GET})
    @RequiresPermissions("biz:invalid:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerInvalidEntity> deptPageInfo = new PageInfo<>(transferCustomerInvalidService.findPage(build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    @ApiOperation(value = "无效操作", notes = "请求参数：\n" +
            "参数说明：[customerId:客户ID], [type: 失败原因(见数据字典：失败(有效)类型（VALID_TYPE）和失败(无效)类型（INVALID）)], [status:失败状态（1-(失败)有效，2-（失败）无效]\n" +
            "示例：\n" +
            "{\n" +
            "    \"customerId\": 0,\n" +
            "    \"status\": 1,\n" +
            "    \"type\": 1,\n" +
            "}")
    @PostMapping("/setCustomerInvalid")
    @RequiresPermissions("biz:invalid:setInvalid")
    @SysLog("无效操作")
    public R setCustomerInvalid(@RequestBody TransferCustomerDTO dto) {
        return transferCustomerInvalidService.setCustomerInvalid(dto);
    }

    @ApiOperation(value = "退回私海操作", notes = "请求参数：\n" +
            "参数说明：[customerId:客户ID]\n" +
            "示例： url?customerId=2\n" +
            "}")
    @PostMapping("/return/private")
    @RequiresPermissions("biz:invalid:private")
    @SysLog("退回私海操作")
    public R quantityInspect(@RequestParam Long customerId) {
        return transferCustomerInvalidService.returnToPrivate(customerId);
    }

}
