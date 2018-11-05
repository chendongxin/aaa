package com.hqjy.mustang.transfer.export.controller;

import com.alibaba.fastjson.JSON;
import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.*;
import com.hqjy.mustang.transfer.export.model.query.*;
import com.hqjy.mustang.transfer.export.service.PromotionCompanyCostService;
import com.hqjy.mustang.transfer.export.service.PromotionCustomerService;
import com.hqjy.mustang.transfer.export.service.PromotionDailyService;
import com.hqjy.mustang.transfer.export.service.PromotionSmsCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广报表控制层
 */

@Api(tags = "推广报表管理", description = "PromotionReportController")
@RestController
@RequestMapping("/report/promotion")
public class PromotionReportController {
    private final static Logger LOG = LoggerFactory.getLogger(PromotionReportController.class);
    private PromotionDailyService promotionDailyService;
    private PromotionCustomerService promotionCustomerService;
    private PromotionSmsCostService promotionSmsCostService;
    private PromotionCompanyCostService promotionCompanyCostService;

    @Autowired
    public void setPromotionCompanyCostService(PromotionCompanyCostService promotionCompanyCostService) {
        this.promotionCompanyCostService = promotionCompanyCostService;
    }

    @Autowired
    public void setPromotionSmsCostService(PromotionSmsCostService promotionSmsCostService) {
        this.promotionSmsCostService = promotionSmsCostService;
    }

    @Autowired
    public void setPromotionService(PromotionCustomerService promotionCustomerService) {
        this.promotionCustomerService = promotionCustomerService;
    }

    @Autowired
    public void setPromotionDailyService(PromotionDailyService promotionDailyService) {
        this.promotionDailyService = promotionDailyService;
    }

    @ApiOperation(value = " 招转日常数据报表列表", notes = "请求参数格式：\n" +
            "分页参数：/report/promotion/promotionDailyList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"companyId\":1869,\n" +
            "  \"getWay\":1869,\n" +
            "  \"sourceId\":1869\n" +
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
            "                    \"businessNum\": 2,\n" +
            "                    \"dealNum\": 0,\n" +
            "                    \"deptId\": 20,\n" +
            "                    \"deptName\": \"广州天河校区\",\n" +
            "                    \"followNum\": 1,\n" +
            "                    \"intentionNum\": 0,\n" +
            "                    \"sequence\": 1,\n" +
            "                    \"unFailNum\": 1,\n" +
            "                    \"unFailRate\": \"50.00%\",\n" +
            "                    \"validNum\": 1,\n" +
            "                    \"validRate\": \"50.00%\",\n" +
            "                    \"visitDealRate\": \"0.00%\",\n" +
            "                    \"visitIntentionRate\": \"0.00%\",\n" +
            "                    \"visitNum\": 1,\n" +
            "                    \"visitValidNum\": 0,\n" +
            "                    \"visitValidRate\": \"0.00%\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"businessNum\": 2,\n" +
            "            \"dealNum\": 0,\n" +
            "            \"followNum\": 1,\n" +
            "            \"intentionNum\": 0,\n" +
            "            \"unFailNum\": 1,\n" +
            "            \"unFailRate\": \"50.00%\",\n" +
            "            \"validNum\": 1,\n" +
            "            \"validRate\": \"50.00%\",\n" +
            "            \"visitDealRate\": \"0.00%\",\n" +
            "            \"visitIntentionRate\": \"0.00%\",\n" +
            "            \"visitNum\": 1,\n" +
            "            \"visitValidNum\": 0,\n" +
            "            \"visitValidRate\": \"0.00%\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/promotionDailyList")
    @RequiresPermissions("tr:prm:daily-list")
    public R promotionDailyList(@ModelAttribute PageParams params, @RequestBody @Valid DailyQueryParams query) {
        return R.result(promotionDailyService.promotionDailyList(params, query));
    }

    @SysLog("导出招转日常数据")
    @ApiOperation(value = "导出招转日常数据", notes = "请求参数格式:\n" +
            "{\n" +
            "  \"beginTime\": \"2018-09-10\",\n" +
            "  \"endTime\": \"2018-10-20\",\n" +
            "  \"deptId\":1869,\n" +
            "  \"companyId\":1869,\n" +
            "  \"getWay\":1869,\n" +
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
    @RequiresPermissions("tr:prm:daily-export")
    public R exportPromotionDaily(@RequestBody @Valid DailyQueryParams query) {
        return R.result(promotionDailyService.exportPromotionDaily(query));
    }

    @ApiOperation(value = "客服推广报表数据列表", notes = "请求参数格式：\n" +
            "分页参数：/report/promotion/promotionDailyList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "\"beginTime\": \"2018-09-10\",\n" +
            "\"endTime\": \"2018-10-20\",\n" +
            "\"userId\":1869,\n" +
            "\"companyId\":1869,\n" +
            "\"sourceId\":1869\n" +
            "}" +
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
            "                    \"deptName\": \"广州人和校区\",\n" +
            "                    \"name\": \"客服Y\",\n" +
            "                    \"sequence\": 1,\n" +
            "                    \"validNum\": 0,\n" +
            "                    \"visitNum\": 0,\n" +
            "                    \"visitValidNum\": 0\n" +
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
            "            \"deptName\": \"/\",\n" +
            "            \"name\": \"/\",\n" +
            "            \"validNum\": 0,\n" +
            "            \"visitNum\": 0,\n" +
            "            \"visitValidNum\": 0\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/promotionCustomerList")
    @RequiresPermissions("tr:prm:ctm-list")
    public R promotionCustomerList(@ModelAttribute PageParams params, @RequestBody @Valid CustomerQueryParams query) {
        return R.result(promotionCustomerService.promotionCustomerList(params, query));
    }

    @SysLog("导出客服推广报表数据")
    @ApiOperation(value = "导出客服推广报表数据", notes = "请求参数格式:\n" +
            "{\n" +
            "\"beginTime\": \"2018-09-10\",\n" +
            "\"endTime\": \"2018-10-20\",\n" +
            "\"userId\":1869,\n" +
            "\"companyId\":1869,\n" +
            "\"sourceId\":1869\n" +
            "}" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportPromotionCustomer")
    @RequiresPermissions("tr:prm:ctm-export")
    public R exportPromotionCustomer(@RequestBody @Valid CustomerQueryParams query) {
        return R.result(promotionCustomerService.exportPromotionCustomer(query));
    }

    @ApiOperation(value = "推广短信费用报表数据列表", notes = "请求参数格式：\n" +
            "分页参数：/report/promotion/promotionDailyList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "\"beginTime\": \"2018-09-10\",\n" +
            "\"endTime\": \"2018-10-20\",\n" +
            "\"deptId\":20\n" +
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
            "                    \"cost\": \"0\",\n" +
            "                    \"deptName\": \"广州天河校区\",\n" +
            "                    \"sendNum\": 0,\n" +
            "                    \"sendSuccessNum\": 0,\n" +
            "                    \"sequence\": 1\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"cost\": \"0.0000\",\n" +
            "            \"deptName\": \"/\",\n" +
            "            \"sendNum\": 0,\n" +
            "            \"sendSuccessNum\": 0\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/promotionSmsCostList")
    @RequiresPermissions("tr:prm:sms-list")
    public R promotionSmsCostList(@ModelAttribute PageParams params, @RequestBody @Valid SmsCostQueryParams query) {
        return R.result(promotionSmsCostService.promotionSmsCostList(params, query));
    }

    @SysLog("导出推广短信费用报表数据")
    @ApiOperation(value = "导出推广短信费用报表数据", notes = "请求参数格式:\n" +
            "{\n" +
            "\"beginTime\": \"2018-09-10\",\n" +
            "\"endTime\": \"2018-10-20\",\n" +
            "\"deptId\":20\n" +
            "}" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%97%A5%E5%B8%B8%E6%95%B0%E6%8D%AE%E6%8A%A5%E8%A1%A8_20181015_174951_502.xls?Expires=1539600591&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=9IQDmE6TT2gi9P0tnrjbntHn7m0%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportPromotionSmsCost")
    @RequiresPermissions("tr:prm:sms-export")
    public R exportPromotionSmsCost(@RequestBody @Valid SmsCostQueryParams query) {
        return R.result(promotionSmsCostService.exportPromotionSmsCost(query));
    }


    @ApiOperation(value = "推广公司费用报表数据列表", notes = "请求参数格式：\n" +
            "分页参数：/report/promotion/promotionCompanyCostList?pageNum=1&pageSize=10\n" +
            "高级查询参数：\n" +
            "{\n" +
            "    \"beginTime\": \"2018-09-30\",\n" +
            "    \"companyId\": 2,\n" +
            "    \"companyName\": \"XXXX公司\",\n" +
            "    \"costType\": 1,\n" +
            "    \"deptId\": 3,\n" +
            "    \"deptName\": \"XXX校区\",\n" +
            "    \"endTime\": \"2018-10-20\",\n" +
            "    \"getWay\": 1,\n" +
            "    \"sourceId\": 1,\n" +
            "    \"sourceName\": \"智联\"\n" +
            "}\n" +
            "响应数据：\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"pageList\": {\n" +
            "            \"currPage\": 1,\n" +
            "            \"list\": [\n" +
            "                {\n" +
            "                    \"date\": \"2018-10-30\",\n" +
            "                    \"genWayCosts\": [\n" +
            "                        {\n" +
            "                            \"cost\": \"0.0000\",\n" +
            "                            \"genWay\": \"精品帮帮\",\n" +
            "                            \"wayId\": 1\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"cost\": \"0.0000\",\n" +
            "                            \"genWay\": \"精准（黄金展位）\",\n" +
            "                            \"wayId\": 2\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"num\": 2,\n" +
            "                    \"totalCost\": \"0.0000\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"pageSize\": 10,\n" +
            "            \"size\": 1,\n" +
            "            \"totalCount\": 1,\n" +
            "            \"totalPage\": 1\n" +
            "        },\n" +
            "        \"total\": {\n" +
            "            \"date\": \"合计\",\n" +
            "            \"genWayCosts\": [\n" +
            "                {\n" +
            "                    \"cost\": \"0.0000\",\n" +
            "                    \"genWay\": \"精品帮帮\",\n" +
            "                    \"wayId\": 1\n" +
            "                },\n" +
            "                {\n" +
            "                    \"cost\": \"0.0000\",\n" +
            "                    \"genWay\": \"精准（黄金展位）\",\n" +
            "                    \"wayId\": 2\n" +
            "                }\n" +
            "            ],\n" +
            "            \"num\": 2,\n" +
            "            \"totalCost\": \"0.0000\"\n" +
            "        }\n" +
            "    }\n" +
            "}"
    )
    @PostMapping("/promotionCompanyCostList")
    @RequiresPermissions("tr:prm:cmp-cost-list")
    public R promotionCompanyCostList(@ModelAttribute PageParams params, @RequestBody @Valid CompanyCostQueryParams query) {
        return R.result(promotionCompanyCostService.promotionCompanyCostList(params, query));
    }

    @SysLog("导出推广公司费用报表数据")
    @ApiOperation(value = "导出推广公司费用报表数据", notes = "请求参数格式:\n" +
            "{\n" +
            "    \"beginTime\": \"2018-09-30\",\n" +
            "    \"companyId\": 2,\n" +
            "    \"companyName\": \"XXXX公司\",\n" +
            "    \"costType\": 1,\n" +
            "    \"deptId\": 3,\n" +
            "    \"deptName\": \"XXX校区\",\n" +
            "    \"endTime\": \"2018-10-20\",\n" +
            "    \"getWay\": 1,\n" +
            "    \"sourceId\": 1,\n" +
            "    \"sourceName\": \"智联\"\n" +
            "}" +
            "请求成功：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": \"http://hqcrm.oss-cn-shenzhen.aliyuncs.com/export/%E6%8B%9B%E8%BD%AC%E6%8E%A8%E5%B9%BF%E5%85%AC%E5%8F%B8%E8%B4%B9%E7%94%A8%E6%8A%A5%E8%A1%A8_20181024_102408_453.xls?Expires=1540351448&OSSAccessKeyId=LTAIAO3rjtbLdTNb&Signature=fKHUTZSh9Az5%2FXHz4lxPQrEEEqk%3D\",\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @PostMapping("/exportPromotionCompanyCost")
    @RequiresPermissions("tr:prm:cmp-cost-export")
    public R exportPromotionCompanyCost(@RequestBody @Valid CompanyCostQueryParams query) {
        return R.result(promotionCompanyCostService.exportPromotionCompanyCost(query));
    }

    @ApiOperation(value = "推广报表模块模型参数Models")
    @GetMapping("hello")
    public void modelAPI(@RequestBody CompanyCostReportResult companyCostReportResult,
                         @RequestBody CustomerReportResult customerReportResult,
                         @RequestBody DailyReportResult dailyReportResult,
                         @RequestBody SmsCostReportResult smsCostReportResult
    ) {
        LOG.info("报表输出参数说明："
                + JSON.toJSONString(companyCostReportResult)
                + JSON.toJSONString(customerReportResult)
                + JSON.toJSONString(dailyReportResult)
                + JSON.toJSONString(smsCostReportResult));
    }
}
