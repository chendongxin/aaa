package com.hqjy.mustang.transfer.export.controller;


import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.InvalidQueryParams;
import com.hqjy.mustang.transfer.export.model.query.ReservationQueryParams;
import com.hqjy.mustang.transfer.export.service.InvalidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmm
 * @date create on 2018/10/11
 * @apiNote 无效客户报表控制层
 */

@Api(tags = "无效客户报表导出", description = "InvalidReportController")
@RestController
@RequestMapping("/report/invalid")
public class InvalidReportController {

    @Autowired
    private InvalidService invalidService;

    @SysLog("无效客户报表导出")
    @ApiOperation(value = "无效客户报表导出", notes = "请求参数格式:\n")
    @PostMapping("/exportInvalid")
    public R exportReservation(@RequestBody InvalidQueryParams query) {
        return R.result(invalidService.exportInvalid(query));
    }

}
