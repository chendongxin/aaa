package com.hqjy.mustang.transfer.call.controller;

import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import com.hqjy.mustang.transfer.call.service.CallCenterService;
import com.hqjy.mustang.transfer.call.service.TransferCallRecordService;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author : heshuangshuang
 * @date : 2018/9/19 16:26
 */
@Api(tags = "通话记录", description = "TransferCallRecordController")
@RestController
@RequestMapping(value = "/callRecord")
public class TransferCallRecordController {

    @Autowired
    private TransferCallRecordService transferCallRecordService;
    @Autowired
    private CallCenterService callRecordService;
    @ApiOperation(value = "查询通话记录", notes = "支持分页，排序和高级查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[呼叫方式]\tcallStyle\": \"外呼电话\",\n" +
            "                \"[座席账号]\tcno\": 9781040,\n" +
            "                \"[通话记录同步时间]\tcreateTime\": \"2018-09-19 15:57:22\",\n" +
            "                \"[创建人]\t createUserId\": 1,\n" +
            "                \"[创建人名称]  createUserName\": \"test\",\n" +
            "                \"[客户归属地]  customerArea\": \"福建龙岩中国移动\",\n" +
            "                \"[客户编号]\tcustomerId\": 0,\n" +
            "                \"[客户号码]\tcustomerNumber\": \"13799070255\",\n" +
            "                \"[记录编号]\tid\": 2770,\n" +
            "                \"[呼叫途径]\tpathway\": \"工作手机\",\n" +
            "                \"[录音URL链接]\t recordFile\": \"http://mdb.tq.cn/mj/filelist.do?type=voiceRecords&uid=94e7816c-1887-4d27-bc75-a3018584c2b0&callagent_time=\",\n" +
            "                \"[响铃时长(秒)]\t ringTime\": 19,\n" +
            "                \"[响铃时长（时分秒）]  ringTimeStr\": \"00:00:19\",\n" +
            "                \"[拨打时间]\tstartTime\": \"2018-09-19 15:26:41\",\n" +
            "                \"[拨号状态]\tstatus\": \"接通\",\n" +
            "                \"[通话时长(秒)]\t totalDuration\": 80,\n" +
            "                \"[通话时长（时分秒）]  totalDurationStr\": \"00:01:20\",\n" +
            "                \"[总时长(秒)]\t totalCall\": 124,\n" +
            "                \"[总时长（时分秒）]  totalCallStr\": \"00:02:04\",\n" +
            "                \"[通话唯一识别码]\t uniqueId\": \"94e7816c-1887-4d27-bc75-a3018584c2b0\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 1,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 883,\n" +
            "        \"totalPage\": 883\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R listPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCallRecordEntity> userPageInfo = new PageInfo<>(transferCallRecordService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 获取录音文件地址
     */
    @GetMapping("/recordFile/{id}")
    @ApiOperation(value = "获取录音文件地址", notes = "参数说明：{id}:通话记录ID  示例：/recordFile/{id} ")
    public R loadRecordFile(@PathVariable("id") Long id) {
        String fileUrl = callRecordService.getRecordFileUrl(id);
        if (StringUtils.isNotEmpty(fileUrl)) {
            return R.result(fileUrl);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 个人通话首页统计
     */
    @GetMapping("/statis/person/{type}")
    @ApiOperation(value = "首页获取个人通话统计",notes = "参数说明: {type}:统计时间  示例: /person/{type} 本日:day 本周:week,本月:month " )
    public R callStatistics(@PathVariable("type") String type) {
        return R.ok(transferCallRecordService.getPersonStatis(type));
    }
}
