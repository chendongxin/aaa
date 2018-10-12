package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.CustomerQueryParams;
import com.hqjy.mustang.transfer.export.service.PromotionCustomerService;
import com.hqjy.mustang.transfer.export.service.PromotionDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广报表控制层
 */

@Api(tags = "推广报表管理", description = "PromotionReportController")
@RestController
@RequestMapping("/report/promotion")
public class PromotionReportController {

    private PromotionDailyService promotionDailyService;
    private PromotionCustomerService promotionCustomerService;

    @Autowired
    public void setPromotionService(PromotionCustomerService promotionCustomerService) {
        this.promotionCustomerService = promotionCustomerService;
    }

    @Autowired
    public void setPromotionDailyService(PromotionDailyService promotionDailyService) {
        this.promotionDailyService = promotionDailyService;
    }

    @ApiOperation(value = " 招转日常数据报表列表", notes = "请求参数格式：\n")
    @PostMapping("/promotionDailyList")
    public R promotionDailyList(@ModelAttribute PageParams params, @RequestBody(required = false) DailyQueryParams query) {
        return R.result(promotionDailyService.promotionDailyList(params, query));
    }

    @SysLog("导出招转日常数据")
    @ApiOperation(value = "导出招转日常数据", notes = "请求参数格式:\n")
    @PostMapping("/exportPromotionDaily")
    public R exportPromotionDaily(@RequestBody DailyQueryParams query) {
        return R.result(promotionDailyService.exportPromotionDaily(query));
    }

    @ApiOperation(value = "客服推广报表数据列表", notes = "请求参数格式：\n")
    @PostMapping("/promotionCustomerList")
    public R promotionCustomerList(@ModelAttribute PageParams params, @RequestBody(required = false) CustomerQueryParams query) {
        return promotionCustomerService.promotionCustomerList(params, query);
    }

    @SysLog("导出招转推广报表数据")
    @ApiOperation(value = "导出客服推广报表数据", notes = "请求参数格式:\n")
    @PostMapping("/exportPromotionCustomer")
    public R exportPromotionCustomer(@RequestBody CustomerQueryParams query) {
        return promotionCustomerService.exportPromotionCustomer(query);
    }
}
