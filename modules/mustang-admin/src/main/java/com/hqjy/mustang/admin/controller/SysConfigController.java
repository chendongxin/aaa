package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.admin.model.entity.SysConfigEntity;
import com.hqjy.mustang.admin.model.entity.SysConfigInfoEntity;
import com.hqjy.mustang.admin.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 系统配置
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:39
 */
@Api(tags = "系统配置", description = "SysConfigController")
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 配置信息
     */
    @ApiOperation(value = "配置信息", notes = "配置信息")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.findOne(id);
        return R.ok(config);
    }

    /**
     * 配置分页查询
     */
    @ApiOperation(value = "配置分页查询", notes = "配置分页查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysConfigEntity> userConfigInfo = new PageInfo<>(sysConfigService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userConfigInfo);
    }

    /**
     * 保存配置
     */
    @ApiOperation(value = "保存配置", notes = "保存配置")
    @SysLog("保存配置")
    @PostMapping
    @RequiresPermissions("sys:config:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysConfigEntity config) {
        int count = sysConfigService.save(config);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改配置
     */
    @ApiOperation(value = "修改配置", notes = "修改配置")
    @SysLog("修改配置")
    @PutMapping
    @RequiresPermissions("sys:config:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysConfigEntity config) {
        int count = sysConfigService.update(config);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除配置
     */
    @ApiOperation(value = "删除配置", notes = "删除配置")
    @SysLog("删除配置")
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:config:delete")
    public R delete(@PathVariable("id") Long id) {
        int count = sysConfigService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 根据配置code获取配置值列表
     */
    @ApiOperation(value = "根据配置code获取配置值列表", notes = "根据配置code获取配置值列表")
    @GetMapping("/info/{code}")
    @RequiresPermissions("sys:config:select")
    public R getInfoList(@PathVariable("code") String code) {
        return R.ok(sysConfigService.getInfoListByCode(code));
    }

    /**
     * 根据配置code获取配置值表格
     */
    @ApiOperation(value = "根据配置code获取配置值表格", notes = "根据配置code获取配置值表格")
    @RequestMapping(value = "/info/list/{code}", method = {RequestMethod.GET})
    @RequiresPermissions("sys:config:select")
    public R infoList(@PathVariable("code") String code) {
        //查询列表数据
        return R.ok(sysConfigService.getInfoListByCode(code));
    }


    /**
     * 保存配置属性
     */
    @ApiOperation(value = "保存配置属性", notes = "保存配置属性")
    @SysLog("保存配置属性")
    @PostMapping("/info")
    @RequiresPermissions("sys:config:save")
    public R saveInfo(@Validated(RestfulValid.POST.class) @RequestBody SysConfigInfoEntity config) {
        int count = sysConfigService.saveInfo(config);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改配置属性
     */
    @ApiOperation(value = "修改配置属性", notes = "修改配置属性")
    @SysLog("修改配置属性")
    @PutMapping("/info")
    @RequiresPermissions("sys:config:update")
    public R updateInfo(@Validated(RestfulValid.PUT.class) @RequestBody SysConfigInfoEntity config) {
        int count = sysConfigService.updateInfo(config);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除配置属性
     */
    @ApiOperation(value = "删除配置属性", notes = "删除配置属性")
    @SysLog("删除配置属性")
    @DeleteMapping("/info/{ids}")
    @RequiresPermissions("sys:config:delete")
    public R deleteInfo(@PathVariable("ids") Long[] ids) {
        int count = sysConfigService.deleteBatchInfo(ids);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }
}
