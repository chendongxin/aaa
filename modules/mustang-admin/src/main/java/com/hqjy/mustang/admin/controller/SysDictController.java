package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.admin.model.entity.SysDictDirEntity;
import com.hqjy.mustang.admin.model.entity.SysDictEntity;
import com.hqjy.mustang.admin.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 数据字典管理
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:39
 */
@Api(tags = "数据字典管理", description = "SysDictController")
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 字典目录列表
     */
    @ApiOperation(value = "字典目录列表", notes = "字典目录列表")
    @GetMapping("/dir/list")
    @RequiresPermissions("sys:dict:list")
    public R confiDirList() {
        return R.ok(sysDictService.getConfigDirList());
    }

    /**
     * 字典目录分页查询
     */
    @ApiOperation(value = "字典目录分页查询", notes = "字典目录分页查询")
    @RequestMapping(value = "/dir/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:dict:list")
    public R dirList(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysDictDirEntity> dictDirInfo = new PageInfo<>(sysDictService.findPageDir(PageQuery.build(pageParam, queryParam)));
        return R.ok(dictDirInfo);
    }

    /**
     * 字典目录信息
     */
    @ApiOperation(value = "字典目录信息", notes = "字典目录信息")
    @GetMapping("/dir/{id}")
    @RequiresPermissions("sys:dict:info")
    public R dirInfo(@PathVariable("id") Long id) {
        SysDictDirEntity dictDir = sysDictService.findOneDir(id);
        if (dictDir != null) {
            return R.ok(dictDir);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 保存字典目录
     */
    @ApiOperation(value = "保存字典目录", notes = "保存字典目录")
    @SysLog("保存字典目录")
    @PostMapping("/dir")
    @RequiresPermissions("sys:dict:save")
    public R saveConfigDir(@Validated(RestfulValid.POST.class) @RequestBody SysDictDirEntity dictDir) {
        int count = sysDictService.saveConfigDir(dictDir);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改字典目录
     */
    @ApiOperation(value = "修改字典目录", notes = "修改字典目录")
    @SysLog("修改字典目录")
    @PutMapping("/dir")
    @RequiresPermissions("sys:dict:update")
    public R updateConfigDir(@Validated(RestfulValid.PUT.class) @RequestBody SysDictDirEntity dictDir) {
        int count = sysDictService.updateConfigDir(dictDir);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除字典目录
     */
    @ApiOperation(value = "删除字典目录", notes = "删除字典目录")
    @SysLog("删除字典目录")
    @DeleteMapping("/dir/{code}")
    @RequiresPermissions("sys:dict:delete")
    public R deleteConfigDir(@PathVariable("code") Long code) {
        int count = sysDictService.deleteConfigDir(code);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**************************/


    /**
     * 字典信息
     */
    @ApiOperation(value = "字典信息", notes = "字典信息")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id) {
        SysDictEntity dict = sysDictService.findOne(id);
        return R.ok(dict);
    }

    /**
     * 字典分页查询
     */
    @ApiOperation(value = "字典分页查询", notes = "字典分页查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysDictEntity> userConfigInfo = new PageInfo<>(sysDictService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userConfigInfo);
    }

    /**
     * 保存字典
     */
    @ApiOperation(value = "保存字典", notes = "保存字典")
    @SysLog("保存字典")
    @PostMapping
    @RequiresPermissions("sys:dict:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysDictEntity dict) {
        int count = sysDictService.save(dict);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改字典
     */
    @ApiOperation(value = "修改字典", notes = "修改字典")
    @SysLog("修改字典")
    @PutMapping
    @RequiresPermissions("sys:dict:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysDictEntity dict) {
        int count = sysDictService.update(dict);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除字典
     */
    @ApiOperation(value = "删除字典", notes = "删除字典")
    @SysLog("删除字典")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@PathVariable("ids") Long[] ids) {
        int count = sysDictService.deleteBatch(ids);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 获取数据字典项
     */
    @ApiOperation(value = "获取数据字典项", notes = "获取数据字典项")
    @GetMapping("/find/{code}")
    public R getDictByCode(@PathVariable("code") String code) {
        return R.ok(sysDictService.getDictByCode(code));
    }

}
