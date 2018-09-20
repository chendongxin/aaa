package com.hqjy.mustang.quartz.controller;

import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.quartz.entity.ScheduleJobLogEntity;
import com.hqjy.mustang.quartz.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 定时任务日志
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@RestController
@Api(tags = "定时任务日志", description = "ScheduleJobController")
@RequestMapping("/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 分页定时任务日志信息
     */
    @ApiOperation(value = "分页查询定时任务日志列表", notes = "支持分页，排序和高级查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<ScheduleJobLogEntity> userPageInfo = new PageInfo<>(scheduleJobLogService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 获取一个任务日志详情
     */
    @ApiOperation(value = "获取一个任务日志详情", notes = "获取一个任务日志详情")
    @GetMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.findOne(logId);
        if (log != null) {
            return R.ok(log);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }
}
