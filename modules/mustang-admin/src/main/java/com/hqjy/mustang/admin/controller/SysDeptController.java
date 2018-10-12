package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.base.AbstractController;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.admin.model.entity.SysDeptEntity;
import com.hqjy.mustang.admin.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 部门管理
 *
 * @author : heshuangshuang
 * @ date : 2018/1/20 10:10
 */
@Api(tags = "部门管理", description = "SysDeptController")
@RestController
@RequestMapping("/dept")
public class SysDeptController extends AbstractController {


    private SysDeptService sysDeptService;

    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 部门管理树数据
     */
    @ApiOperation(value = "部门管理树数据", notes = "部门管理树数据，包含所有部门数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(sysDeptService.getRecursionTree(true));
    }


    /**
     * add xyq 2018年7月6日15:56:30
     */
    @ApiOperation(value = "用户部门树", notes = "用户部门树")
    @ApiImplicitParam(name = "isRoot", paramType = "query", value = "true:返回系统所有部门树，false:返回当前用户部门树", dataType = "Boolean")
    @GetMapping("/deptTree")
    public R deptTree(@RequestParam(value = "isRoot",required = false) Boolean isRoot) {
        return R.ok(sysDeptService.getUserDeptTree(isRoot,Boolean.TRUE));
    }


    /**
     * 部门选择树数据
     */
    @ApiOperation(value = "部门选择树数据", notes = "部门选择树数据，部门选择树数据")
    @GetMapping("/select")
    public R select() {
        return R.ok(sysDeptService.getSelectTree(true));
    }

    /**
     * 分页查询部门列表
     */
    @ApiOperation(value = "分页查询部门列表", notes = "分页查询部门列表")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:dept:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysDeptEntity> deptPageInfo = new PageInfo<>(sysDeptService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    /**
     * 查询部门信息
     */
    @ApiOperation(value = "查询部门信息", notes = "查询部门信息")
    @GetMapping("/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.findOne(deptId);
        if (dept != null) {
            return R.ok(dept);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 保存部门
     */
    @ApiOperation(value = "保存部门", notes = "保存部门")
    @SysLog("保存部门")
    @PostMapping
    @RequiresPermissions("sys:dept:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysDeptEntity dept) {
        int count = sysDeptService.save(dept);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改部门
     */
    @ApiOperation(value = "修改部门", notes = "修改部门")
    @SysLog("修改部门")
    @PutMapping
    @RequiresPermissions("sys:dept:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysDeptEntity dept) {
        int count = sysDeptService.update(dept);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除部门
     */
    @ApiOperation(value = "删除部门", notes = "删除部门\n 示例： /1")
    @SysLog("删除部门")
    @DeleteMapping("/{deptId}")
    @RequiresPermissions("sys:dept:delete")
    public R delete(@PathVariable("deptId") Long deptId) {
        int count = sysDeptService.delete(deptId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * @author : gmm
     * @date : 2018/10/10 16:31
     * 获取所有部门
     */
    @ApiOperation(value = "获取所有部门信息", notes = "获取所有部门信息")
    @GetMapping(value = "/all")
    public R getAllDept() {
        return R.ok(sysDeptService.getAllDeptList());
    }

}
