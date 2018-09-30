package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.admin.model.entity.SysLogEntity;
import com.hqjy.mustang.admin.service.SysLogService;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 系统日志
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:39
 */
@Api(tags = "系统日志", description = "SysLogController")
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation(value = "系统切面日志分页查询-列表", notes = "系统切面日志分页查询-列表")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<SysLogEntity> deptPageInfo = new PageInfo<>(sysLogService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    /**
     * 日志信息
     */
    @ApiOperation(value = "日志信息", notes = "日志信息")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:log:info")
    public R info(@PathVariable("id") Long id) {
        SysLogEntity log = sysLogService.findOne(id);
        if (log != null) {
            return R.ok(log);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

}
