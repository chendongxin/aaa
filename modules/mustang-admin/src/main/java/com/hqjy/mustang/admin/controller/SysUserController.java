package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.base.AbstractController;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.admin.model.entity.SysUserEntity;
import com.hqjy.mustang.admin.service.SysUserService;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * 系统用户
 *
 * @author : heshuangshuang
 * @date : 2018/1/19 15:31
 */
@Api(tags = "用户管理", description = "SysUserController")
@RestController
@RequestMapping("/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService userService;

    /**
     * 用户信息
     */
    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = userService.findOne(userId);
        if (user != null) {
            return R.ok(user);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 分页查询用户信息
     */
    @ApiOperation(value = "分页查询用户信息", notes = "支持分页，排序和高级查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:user:list")
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<SysUserEntity> userPageInfo = new PageInfo<>(userService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }


    @ApiOperation(value = "获取系统所有用户", notes = "参数：[userId:用户ID,userName:用户姓名]\n" +
            "{\n" +
            "    \"code\": 0,\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"userId\": 1,\n" +
            "            \"userName\": \"admin\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"userId\": 2,\n" +
            "            \"userName\": \"测试账号\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")
    @RequestMapping(value = "/listAll", method = {RequestMethod.GET})
    public R listAll() {
        return R.ok(userService.listAll());
    }

    /**
     * 保存用户
     */
    @ApiOperation(value = "增加一个用户", notes = "增加一个用户")
    @ApiImplicitParam(paramType = "body", name = "user", value = "用户信息对象", required = true, dataType = "SysUserEntity")
    @SysLog("保存用户")
    @PostMapping
    @RequiresPermissions("sys:user:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysUserEntity user) {
        int count = userService.save(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", notes = "删除用户：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "userId", value = "用户ID", required = true, dataType = "String")
    @SysLog("删除用户")
    @DeleteMapping("/{userId}")
    @RequiresPermissions("sys:user:delete")
    public R delete(@PathVariable("userId") Long userId) {
        int count = userService.delete(userId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "更新用户信息", notes = "更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", value = "用户详细实体", required = true, dataType = "SysUserEntity"),
            @ApiImplicitParam(paramType = "path", name = "userId", value = "用户ID", required = true, dataType = "Long")
    })
    @SysLog("修改用户")
    @PutMapping
    @RequiresPermissions("sys:user:update")
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysUserEntity user) {
        int count = userService.update(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 修改用户部门
     */
    @ApiOperation(value = "修改用户部门", notes = "修改用户部门")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", value = "用户详细实体", required = true, dataType = "SysUserEntity"),
    })
    @SysLog("修改用户部门")
    @PutMapping("/dept")
    @RequiresPermissions("sys:user:update")
    public R updateUserDept(@Validated(RestfulValid.PUT.class) @RequestBody SysUserEntity user) {
        int count = userService.updateUserDept(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @SysLog("修改密码")
    @PutMapping(value = "/password")
    public R updatePassword(@RequestBody HashMap<String, Object> param) {
        int result = userService.updatePassword(param);
        if (result > 0) {
            userService.updateUserRedisInfo();
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 修改个人信息
     */
    @ApiOperation(value = "修改个人信息", notes = "修改个人信息")
    @SysLog("修改个人信息")
    @PutMapping(value = "/info")
    public R updateUserInfo(@RequestBody SysUserEntity sysUser) {
        int result = userService.updateUserInfo(sysUser);
        if (result > 0) {
            userService.updateUserRedisInfo();
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_NOT_CHANGE);
    }

    /**
     * 头像上传
     */
    @ApiOperation(value = "头像上传", notes = "头像上传")
    @SysLog("头像上传")
    @PostMapping(value = "/avatar")
    public R avatarUpload(@RequestParam("file") MultipartFile file) {
        int count = userService.updateUserAvatar(file);
        if (count > 0) {
            return R.ok("头像设置成功");
        }
        return R.error("头像设置失败");
    }


    @ApiOperation(value = "根据部门ID（部门树treeValue）获取当前部门及所有子部门的所有用户", notes = "根据部门ID（部门树treeValue）获取当前部门及所有子部门的所有用户")
    @GetMapping(value = "/getUserByDeptTree")
    public R getUserByAllDeptId(@RequestParam("treeValue") Long treeValue) {
        return R.ok(userService.getUserByAllDeptId(treeValue));
    }

    @ApiOperation(value = "只获取当前部门节点的用户，如果是超级管理员，获取所有用户", notes = "只获取当前部门节点的用户")
    @GetMapping(value = "/getUserByDeptId")
    public R getUserByDeptId(@RequestParam(value = "treeValue", required = false) Long deptId) {
        List<SysUserEntity> list = userService.getUserByDeptId(deptId);

        return list != null && list.size() > 0 ? R.ok(list) : R.error("当前选择部门不存在人员！");
    }

}
