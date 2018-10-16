package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCallRecordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCallRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 外呼记录
 *
 * @author : gmm
 * @date : 2018/10/9 11:04
 */
@Api(tags = "呼叫中心接口", description = "TransferCallRecordController")
@RestController
@RequestMapping("/call")
public class TransferCallRecordController extends AbstractMethodError {

    @Autowired
    private TransferCallRecordService callRecordService;

    /**
     * 分页查询通话记录
     */
    @ApiOperation(value = "分页查询通话记录", notes = "支持分页，排序和高级查询 \n 返回结果:\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 7,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[通话记录编号] id\": 1,\n" +
            "                \"[外呼自定义参数] params\": \"{\\\"customerId\\\":0,\\\"userId\\\":1,\\\"userName\\\":\\\"test\\\"}\",\n" +
            "                \"[野马客户ID] customerId\": 262,\n" +
            "                \"[电话记录唯一标识] uniqueId\": \"10.10.57.17-1530785076.265157\",\n" +
            "                \"[坐席号] cno\": \"8248\",\n" +
            "                \"[座席电话] clientNumber\": \"17600222250\",\n" +
            "                \"[客户号码] customerNumber\": \"15820371039\",\n" +
            "                \"[客户归属地]customerArea\": \"肇庆\",\n" +
            "                \"[当前归属人编号] userId\": 1,\n" +
            "                \"[当前归属人名称] userName\": \"YY\",\n" +
            "                \"[呼叫方式] call_style\": \"直线呼入\",\n" +
            "                \"[呼叫途径] pathway\": \"工作手机\",\n" +
            "                \"[是否接通(0:接通  1: 未接通  2:其他)] status\": 0,\n" +
            "                \"[电话开始呼叫时间] startTime\": \"2018-07-05 18:04:35\",\n" +
            "                \"[通话时长(秒)] total_duration\": 1000,\n" +
            "                \"[录音URL链接] record_file\": \"http://mdb.tq.cn/mj/filelist.do?type=voiceRecords&uid=64bc2340-76f5-4608-8c1a-10b375034903&callagent_time=\",\n" +
            "                \"[响铃时长(秒)] ring_time\": 1000,\n" +
            "                \"[电话提供商记录写入时间] insert_db_time\": 1,\n" +
            "                \"[创建人编号] createUserId\": 1,\n" +
            "                \"[创建人名称] createUserName\": \"admin\",\n" +
            "                \"[创建时间] createTime\": \"2018-07-06 18:39:02\",\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 10,\n" +
            "        \"size\": 7,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 7,\n" +
            "        \"totalPage\": 1\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/record/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCallRecordEntity> userPageInfo = new PageInfo<>(callRecordService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }
}
