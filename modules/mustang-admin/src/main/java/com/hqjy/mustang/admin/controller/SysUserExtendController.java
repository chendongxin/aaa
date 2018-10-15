package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.base.AbstractController;
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
 * description: 人员映射
 * @author: hutao
 * @date 2018/10/11 11:29
 */
@Api(tags = "人员映射", description = "SysUserExtendController")
@RestController
@RequestMapping("/userExtend")
public class SysUserExtendController extends AbstractController {
    @Autowired
    private SysUserExtendService sysUserExtendService;

    /**
     * description: 分页查询
     * @author: hutao
     * @date 2018/10/11 11:33
     */
    @ApiOperation(value = "人员映射分页查询", notes = "人员映射分页查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("sys:userExtend:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam){
        return R.ok(new PageInfo<>(sysUserExtendService.findPage(PageQuery.build(pageParam, queryParam))));
    }

    /**
     * description: 人员映射-新增
     * @author: hutao
     * @date 2018/10/11 14:15 
     */
    @ApiOperation(value = "新增人员映射", notes = "参数：[userId:用户ID, tqId:tq_id]")
    @SysLog("新增人员映射配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:userExtend:save")
    public R save(@RequestBody SysUserExtendEntity userExtend){
        int count = sysUserExtendService.save(userExtend);
        if(count > 0){
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * description: 人员映射-修改
     * @author: hutao
     * @date 2018/10/11 18:14
     */
    @ApiOperation(value = "修改人员映射配置", notes = "请求参数示例：\n" +
            "{\n" +
            "    \"cno\": 8004,\n" +
            "    \"id\": 2,\n" +
            "    \"ncUserId\": \"zkxnxs10\",\n" +
            "    \"phone\": \"17600222250\",\n" +
            "    \"userId\": 1,\n" +
            "    \"userName\": \"admin\",\n" +
            "    \"xnId\": \"ntalker_steven\"\n" +
            "\n" +
            "备注：编辑人员映射时，不允许编辑修改用户姓名")
    @SysLog("修改人员映射配置")
    @PutMapping("/update")
    @RequiresPermissions("sys:userExtend:update")
    public R update(@RequestBody SysUserExtendEntity userExtend) {
        int count = sysUserExtendService.update(userExtend);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * description: 人员映射-删除
     * @author: hutao
     * @date 2018/10/11 18:15
     */
    @ApiOperation(value = "删除用户扩展配置", notes = "删除用户扩展配置，支持批量删除\n 示例： /1,2,3,4,5")
    @SysLog("删除用户扩展配置")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:userExtend:delete")
    public R delete(@PathVariable("ids") Long[] ids) {
        int count = sysUserExtendService.deleteBatch(ids);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }
}
