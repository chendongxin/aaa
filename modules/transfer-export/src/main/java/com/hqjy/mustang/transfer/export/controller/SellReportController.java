package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellAttacheQueryParams;
import com.hqjy.mustang.transfer.export.service.SellAttacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmm
 * @date create on 2018/10/20
 * @apiNote 电销报表控制层
 */

@Api(tags = "电销报表管理", description = "SellReportController")
@RestController
@RequestMapping("/report/sell")
public class SellReportController {

    private SellAttacheService sellAttacheService;

    @Autowired
    public void setSellAttacheService(SellAttacheService sellAttacheService) {
        this.sellAttacheService = sellAttacheService;
    }

    @ApiOperation(value = "电销专员排行报表列表", notes = "请求参数格式：\n" +
            "分页参数：/report/sell/sellAttacheList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869\n" +
            "  \"getWay\":1869\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"sellName\": \"丫丫\",\n" +
            "        \"deptName\": \"广州天河校区\",\n" +
            "        \"visitValidRate\": \"0.00%\",\n" +
            "        \"visitValidNum\": 0,\n" +
            "        \"visitTodayAppointNum\": 0,\n" +
            "        \"visitTomorrowAppointNum\": 0,\n" +
            "        \"AllotNum\": 0,\n" +
            "        \"validNum\": 0,\n" +
            "        \"validRate\": \"0.00%\",\n" +
            "        \"visitRate\": \"0.00%\",\n" +
            "        \"dealNum\": 0,\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageSize\": 10,\n" +
            "    \"size\": 1,\n" +
            "    \"totalCount\": 1,\n" +
            "    \"totalPage\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}")
    @PostMapping("/sellAttacheList")
    public R sellAttacheList(@ModelAttribute PageParams params, @RequestBody(required = false) SellAttacheQueryParams query) {
        return R.result(sellAttacheService.sellAttacheList(params, query));
    }

    @SysLog("导出电销专员排行数据")
    @ApiOperation(value = "导出电销专员排行数据", notes = "请求参数格式:\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869\n" +
            "  \"sourceId\":1869\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportPromotionDaily")
    public R exportSellAttache(@RequestBody SellAttacheQueryParams query) {
        return R.result(sellAttacheService.exportSellAttache(query));
    }
}
