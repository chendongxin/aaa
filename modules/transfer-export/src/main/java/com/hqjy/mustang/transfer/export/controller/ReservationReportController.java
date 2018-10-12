package com.hqjy.mustang.transfer.export.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.ReservationQueryParams;
import com.hqjy.mustang.transfer.export.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 邀约报表控制层
 */

@Api(tags = "邀约报表导出", description = "ReservationReportController")
@RestController
@RequestMapping("/report/reservation")
public class ReservationReportController {

    private ReservationService reservationService;

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @SysLog("邀约报表导出")
    @ApiOperation(value = "邀约报表导出", notes = "请求参数格式:\n")
    @PostMapping("/exportReservation")
    public R exportReservation(@RequestBody ReservationQueryParams query) {
        return R.result(reservationService.exportReservation(query));
    }
}
