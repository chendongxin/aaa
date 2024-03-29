package com.hqjy.mustang.transfer.export.controller;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.*;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import com.hqjy.mustang.transfer.export.service.SellAttacheService;
import com.hqjy.mustang.transfer.export.service.SellCallService;
import com.hqjy.mustang.transfer.export.service.SellDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author gmm
 * @date create on 2018/10/20
 * @apiNote 电销报表控制层
 */
@Api(tags = "电销报表管理", description = "SellReportController")
@RestController
@RequestMapping("/report/sell")
public class SellReportController {
    private final static Logger LOG = LoggerFactory.getLogger(SellReportController.class);
    private SellAttacheService sellAttacheService;
    private SellDeptService sellDeptService;
    private SellCallService sellCallService;

    @Autowired
    public void setSellAttacheService(SellAttacheService sellAttacheService) {
        this.sellAttacheService = sellAttacheService;
    }

    @Autowired
    public void setSellDeptService(SellDeptService sellDeptService) {
        this.sellDeptService = sellDeptService;
    }

    @Autowired
    public void setSellCallService(SellCallService sellCallService) {
        this.sellCallService = sellCallService;
    }

    @ApiOperation(value = "电销专员排行报表列表", notes = "请求参数格式：\n" +
            "分页参数：/report/sell/sellAttacheList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"sourceId\":1,\n" +
            "  \"getWay\":1\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"pageList\": {\n" +
            "            \"currPage\": 1,\n" +
            "            \"list\": [\n" +
            "                {\n" +
            "                    \"allotNum\": 0,\n" +
            "                    \"dealNum\": 0,\n" +
            "                    \"deptId\": 20,\n" +
            "                    \"deptName\": \"广州天河校区\",\n" +
            "                    \"name\": \"电销专员一\",\n" +
            "                    \"reservationNum\": 0,\n" +
            "                    \"userId\": 26,\n" +
            "                    \"validNum\": 0,\n" +
            "                    \"validRate\": \"0.00%\",\n" +
            "                    \"visitNum\": 0,\n" +
            "                    \"visitRate\": \"0.00%\",\n" +
            "                    \"visitValidRate\": \"0.00%\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"allotNum\": 0,\n" +
            "            \"dealNum\": 0,\n" +
            "            \"reservationNum\": 0,\n" +
            "            \"validNum\": 0,\n" +
            "            \"validRate\": \"0.00%\",\n" +
            "            \"visitNum\": 0,\n" +
            "            \"visitRate\": \"0.00%\",\n" +
            "            \"visitValidRate\": \"0.00%\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/sellAttacheList")
    @RequiresPermissions("tr:sell:att-list")
    public R sellAttacheList(@ModelAttribute PageParams params, @RequestBody @Valid SellQueryParams query) {
        return R.result(sellAttacheService.sellAttacheList(params, query));
    }

    @SysLog("导出电销专员排行数据")
    @ApiOperation(value = "导出电销专员排行数据", notes = "请求参数格式:\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"sourceId\":1,\n" +
            "  \"getWay\":1\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportSellAttache")
    @RequiresPermissions("tr:sell:att-export")
    public R exportSellAttache(@RequestBody @Valid SellQueryParams query) {
        return R.result(sellAttacheService.exportSellAttache(query));
    }

    @ApiOperation(value = "部门电销排行报表列表", notes = "请求参数格式：\n" +
            "分页参数：/report/sell/sellDeptList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"sourceId\":1,\n" +
            "  \"getWay\":1\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"pageList\": {\n" +
            "            \"currPage\": 1,\n" +
            "            \"list\": [\n" +
            "                {\n" +
            "                    \"businessNum\": 0,\n" +
            "                    \"dealNum\": 0,\n" +
            "                    \"deptId\": 20,\n" +
            "                    \"deptName\": \"广州天河校区\",\n" +
            "                    \"reservationNum\": 0,\n" +
            "                    \"validNum\": 1,\n" +
            "                    \"validRate\": \"0.00%\",\n" +
            "                    \"visitNum\": 0,\n" +
            "                    \"visitRate\": \"0.00%\",\n" +
            "                    \"visitValidRate\": \"0.00%\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"businessNum\": 0,\n" +
            "            \"dealNum\": 0,\n" +
            "            \"reservationNum\": 0,\n" +
            "            \"validNum\": 1,\n" +
            "            \"validRate\": \"0.00%\",\n" +
            "            \"visitNum\": 0,\n" +
            "            \"visitRate\": \"0.00%\",\n" +
            "            \"visitValidRate\": \"0.00%\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/sellDeptList")
    @RequiresPermissions("tr:sell:dept-list")
    public R sellDeptList(@ModelAttribute PageParams params, @RequestBody @Valid SellQueryParams query) {
        return R.result(sellDeptService.sellDeptList(params, query));
    }

    @SysLog("导出部门电销排行数据")
    @ApiOperation(value = "导出部门电销排行数据", notes = "请求参数格式:\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"sourceId\":1,\n" +
            "  \"getWay\":1\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportSellDept")
    @RequiresPermissions("tr:sell:dept-export")
    public R exportSellDept(@RequestBody @Valid SellQueryParams query) {
        return R.result(sellDeptService.exportSellDept(query));
    }

    @ApiOperation(value = "电销商机拨打排行报表列表", notes = "请求参数格式：\n" +
            "分页参数：/report/sell/sellCallList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"pageList\": {\n" +
            "            \"currPage\": 1,\n" +
            "            \"list\": [\n" +
            "                {\n" +
            "                    \"callNum\": 0,\n" +
            "                    \"connectNum\": 0,\n" +
            "                    \"connectRate\": \"0.00%\",\n" +
            "                    \"deptId\": 20,\n" +
            "                    \"deptName\": \"广州天河校区\",\n" +
            "                    \"duration\": \"00:00:00\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"callNum\": 0,\n" +
            "            \"connectNum\": 0,\n" +
            "            \"connectRate\": \"0.00%\",\n" +
            "            \"duration\": \"00:00:00\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/sellCallList")
    @RequiresPermissions("tr:sell:call-list")
    public R sellCallList(@ModelAttribute PageParams params, @RequestBody @Valid SellQueryParams query) {
        return R.result(sellCallService.sellCallList(params, query));
    }

    @SysLog("导出电销商机拨打排行数据")
    @ApiOperation(value = "导出电销商机拨打排行数据", notes = "请求参数格式:\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869\n" +
            "}\n" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportSellCall")
    @RequiresPermissions("tr:sell:call-export")
    public R exportSellCall(@RequestBody @Valid SellQueryParams query) {
        return R.result(sellCallService.exportSellCall(query));
    }


    @ApiOperation(value = "电销报表模块模型参数Models")
    @GetMapping("hello")
    public void modelAPI(@RequestBody SellCallReportResult sellCallReportResult,
                         @RequestBody SellDeptReportResult sellDeptReportResult,
                         @RequestBody SellAttacheReportResult sellAttacheReportResult
    ) {
        LOG.info("报表输出参数说明："
                + JSON.toJSONString(sellCallReportResult)
                + JSON.toJSONString(sellDeptReportResult)
                + JSON.toJSONString(sellAttacheReportResult));
    }

}
