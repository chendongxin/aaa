package com.hqjy.transfer.crm.modules.sys.controller;

import com.hqjy.transfer.common.base.annotation.SysLog;
import com.hqjy.transfer.common.base.base.AbstractController;
import com.hqjy.transfer.common.base.constant.StatusCode;
import com.hqjy.transfer.common.base.utils.PageInfo;
import com.hqjy.transfer.common.base.utils.PageQuery;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.common.base.validator.RestfulValid;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysRoleEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 角色管理
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
@Api(tags = "角色管理", description = "SysRoleController")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 可用角色下拉树数据
     */
    @ApiOperation(value = "可用角色下拉树数据", notes = "可用角色下拉树数据")
    @GetMapping("/drop")
    //@RequiresPermissions("sys:role:select")
    public R drop() {
        return R.ok(sysRoleService.getDrop(false));
    }


    /**
     * 所有角色下拉树数据
     */
    @ApiOperation(value = "所有角色下拉树数据", notes = "所有角色下拉树数据")
    @GetMapping("/drop/all")
    public R dropAll() {
        return R.ok(sysRoleService.getDrop(true));
    }

    /**
     * 可用角色树数据
     */
    @ApiOperation(value = "可用角色树数据", notes = "可用角色树数据，可用角色树数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(sysRoleService.getAllRoleList());
    }

    /**
     * 角色信息
     */
    @ApiOperation(value = "角色信息", notes = "角色信息")
    @GetMapping("/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity roleEntity = sysRoleService.findOne(roleId);
        if (roleEntity != null) {
            return R.ok(roleEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 角色分页查询
     */
    @ApiOperation(value = "角色分页查询", notes = "角色分页查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysRoleEntity> userRoleInfo = new PageInfo<>(sysRoleService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userRoleInfo);
    }

    /**
     * 保存角色
     */
    @ApiOperation(value = "保存角色", notes = "保存角色")
    @SysLog("保存角色")
    @PostMapping
    @RequiresPermissions("sys:role:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysRoleEntity role) {
        int count = sysRoleService.save(role);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改角色
     */
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @SysLog("修改角色")
    @PutMapping
    @RequiresPermissions("sys:role:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysRoleEntity role) {
        int count = sysRoleService.update(role);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @SysLog("删除角色")
    @DeleteMapping("/{roleId}")
    @RequiresPermissions("sys:role:delete")
    public R delete(@PathVariable("roleId") Long roleId) {
        int count = sysRoleService.delete(roleId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

}
