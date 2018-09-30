package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;
import com.hqjy.mustang.transfer.crm.service.TransferFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * @author gmm
 * @ description
 * @ date create in 2018年9月27日15:30:55
 */
@Api(tags = "客户管理-跟进记录", description = "TransferFollowController")
@RestController
@RequestMapping("/customer/follow")
public class TransferFollowController extends AbstractMethodError {

    @Autowired
    private TransferFollowService transferFollowService;

    @ApiOperation(value = "分页查询-跟进记录列表", notes = "请求参数说明：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "查询参数（RequestBody数据格式接收）：[customerId:客户Id必传]\n" +
            "响应参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "[followUserName:跟进人], [createTime:跟进时间], [contactType:沟通方式：枚举值见数据字典COMMUNICATE_WAY ]\n" +
            "[followStatus:跟进状态：枚举值见数据字典FOLLOW_STATUS(默认9-无)], [memo:跟进详情],[nextTime:下次联系时间]\n" +
            "响应结果示例：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"contactType\": 2,\n" +
            "                \"createUserId\": 1,\n" +
            "                \"createUserName\": \"丫丫\",\n" +
            "                \"createTime\": \"2018-06-14 16:51:42\",\n" +
            "                \"nextTime\": \"2018-06-14 16:51:42\",\n" +
            "                \"customerId\": 1,\n" +
            "                \"followId\": 1,\n" +
            "                \"followStatus\": 1,\n" +
            "                \"followUserName\": \"admin\",\n" +
            "                \"memo\": \"TETST\",\n" +
            "                \"processId\": 1\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 10,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 1,\n" +
            "        \"totalPage\": 1\n" +
            "    }\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferFollowEntity> deptPageInfo = new PageInfo<>(transferFollowService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    @ApiOperation(value = "新增跟进记录", notes = "请求参数说明：\n" +
            "[customerId:客户ID]\n" +
            "[contactType:沟通方式：枚举值见数据字典COMMUNICATE_WAY ]\n" +
            "[followStatus:跟进状态：枚举值见数据字典FOLLOW_STATUS(默认9-无)]\n" +
            "[memo:跟进详情]\n" +
            "请求示例：\n" +
            "{\n" +
            "    \"contactType\": 0,\n" +
            "    \"customerId\": 0,\n" +
            "    \"followStatus\": 0,\n" +
            "    \"memo\": \"string\",\n" +
            "}")
    @SysLog("新增跟进记录")
    @PostMapping("/save")
    public R save(@RequestBody TransferFollowEntity entity) {
        transferFollowService.save(entity);
        return R.ok();
    }

}
