package com.hqjy.transfer.crm.modules.sys.controller;

import com.hqjy.transfer.common.base.base.AbstractController;
import com.hqjy.transfer.common.base.constant.StatusCode;
import com.hqjy.transfer.common.base.utils.PageInfo;
import com.hqjy.transfer.common.base.utils.PageQuery;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysLogEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author xieyuqing
 * @description
 * @date create in 17:57 2018/5/18
 */
@Api(tags = "系统日志", description = "SysLogController")
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends AbstractController {

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
