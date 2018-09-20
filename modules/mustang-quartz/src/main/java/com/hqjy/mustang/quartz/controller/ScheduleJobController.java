package com.hqjy.mustang.quartz.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.quartz.entity.ScheduleJobEntity;
import com.hqjy.mustang.quartz.service.ScheduleJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 定时任务
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@RestController
@Api(tags = "定时任务", description = "ScheduleJobController")
@RequestMapping("/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 分页定时任务列表
     */
    @ApiOperation(value = "分页查询定时任务列表", notes = "支持分页，排序和高级查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<ScheduleJobEntity> userPageInfo = new PageInfo<>(scheduleJobService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }


    /**
     * 定时任务信息
     */
    @ApiOperation(value = "获取一个定时任务信息", notes = "定时任务信息")
    @GetMapping("/info/{jobId}")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.findOne(jobId);
        if (schedule != null) {
            return R.ok(schedule);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @ApiOperation(value = "保存定时任务", notes = "保存定时任务")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody ScheduleJobEntity scheduleJob) {
        int count = scheduleJobService.save(scheduleJob);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @ApiOperation(value = "修改定时任务", notes = "修改定时任务")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody ScheduleJobEntity scheduleJob) {
        int count = scheduleJobService.update(scheduleJob);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @DeleteMapping("/{jobId}")
    public R delete(@PathVariable("jobId") Long jobId) {
        int count = scheduleJobService.delete(jobId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @ApiOperation(value = "立即执行任务", notes = "立即执行任务")
    @PutMapping("/run/{jobIds}")
    public R run(@PathVariable("jobIds") Long[] jobIds) {
        scheduleJobService.run(jobIds);
        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @ApiOperation(value = "暂停定时任务", notes = "暂停定时任务")
    @PutMapping("/pause/{jobIds}")
    public R pause(@PathVariable("jobIds") Long[] jobIds) {
        scheduleJobService.pause(jobIds);
        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @ApiOperation(value = "恢复定时任务", notes = "恢复定时任务")
    @PutMapping("/resume/{jobIds}")
    public R resume(@PathVariable("jobIds") Long[] jobIds) {
        scheduleJobService.resume(jobIds);
        return R.ok();
    }

}
