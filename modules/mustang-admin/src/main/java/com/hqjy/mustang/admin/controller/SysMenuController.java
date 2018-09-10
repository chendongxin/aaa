package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.base.AbstractController;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.admin.model.entity.SysMenuEntity;
import com.hqjy.mustang.admin.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 菜单管理
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
@Api(tags = "菜单管理", description = "SysMenuController")
@RestController
@RequestMapping("//menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 菜单树数据
     */
    @ApiOperation(value = "菜单树数据", notes = "包含树菜单和权限数据")
    @GetMapping("/tree")
    //@RequiresPermissions("sys:menu:list")
    public R tree() {
        return R.ok(sysMenuService.getRecursionTree());
    }

    /**
     * 菜单选择树数据
     */
    @ApiOperation(value = "菜单选择树数据", notes = "包含菜单数据和列表数据，不包含权限")
    @GetMapping("/select")
    //@RequiresPermissions("sys:menu:list")
    public R select() {
        return R.ok(sysMenuService.getRecursionTree(0, 1));
    }

    /**
     * 分页查询菜单列表
     */
    @ApiOperation(value = " 分页查询菜单列表", notes = "这里不能进行接口测试，因为Swaggger参数不支持" +
            "\n分页参数：[pageNum:当前页],[pageSize:每页的数量],[sidx:排序字段],[sort:排序方式]" +
            "\n\tGET：普通查询，查询参数在URL： 示例： /sys/menu/list/pageNum=1&pageSize=10&sidx=menuId&sort=desc&menuName=系统管理" +
            "\n\tPOST： 高级查询 查询参数在body： 示例： /sys/menu/list/pageNum=1&pageSize=10&sidx=menuId&sort=desc 查询参数：{\"menuName\":\"系统管理\",\"code\":\"system\"}" +
            "\n返回字段: {\n" +
            " 业务消息   \"msg\": \"成功\",\n" +
            " 业务数据   \"result\": {\n" +
            " 当前页      \"currPage\": 1,\n" +
            " 当前页面最后一个元素在数据库中的行号       \"endRow\": 1,\n" +
            " 结果集       \"list\": [\n" +
            "            {\n" +
            " 菜单编码               \"code\": \"system\",\n" +
            " 创建时间               \"createTime\": \"2018-03-23 14:51:54\",\n" +
            " 菜单图标               \"icon\": \"wifi\",\n" +
            "  菜单编号              \"menuId\": 1,\n" +
            " 菜单名称               \"menuName\": \"系统管理\",\n" +
            " 父菜单编号               \"parentId\": 0,\n" +
            " 排序号              \"seq\": 1,\n" +
            " 状态              \"status\": 0,\n" +
            " 菜单类型              \"type\": 0,\n" +
            " 更新时间              \"updateTime\": \"2018-03-23 14:52:12\"\n }\n ],\n" +
            " 每页的数量       \"pageSize\": 10,\n" +
            " 当前页的数量      \"size\": 1,\n" +
            " 当前页面第一个元素在数据库中的行号       \"startRow\": 1,\n" +
            " 总记录数       \"totalCount\": 1,\n" +
            " 总页数       \"totalPage\": 1\n" +
            " },\n" +
            " 业务状态码   \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:menu:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        return R.ok(new PageInfo<>(sysMenuService.findPage(PageQuery.build(pageParam, queryParam))));
    }

    /**
     * 菜单信息
     */
    @ApiOperation(value = "菜单信息", notes = "查询一个菜单的详细信息")
    @GetMapping("/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity sysMenu = sysMenuService.findOne(menuId);
        if (sysMenu != null) {
            return R.ok(sysMenu);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存菜单", notes = "保存菜单")
    @SysLog("保存菜单")
    @PostMapping
    @RequiresPermissions("sys:menu:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysMenuEntity menu) {
        int count = sysMenuService.save(menu);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @SysLog("修改菜单")
    @PutMapping
    @RequiresPermissions("sys:menu:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysMenuEntity menu) {
        int count = sysMenuService.update(menu);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除菜单", notes = "删除菜单，支持批量删除\n 示例： /1,2,3,4,5")
    @SysLog("删除菜单")
    @DeleteMapping("/{menuIds}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable("menuIds") Long[] menuIds) {
        int count = sysMenuService.deleteBatch(menuIds);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }
}
