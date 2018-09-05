package com.hqjy.transfer.crm.modules.sys.controller;

import com.hqjy.transfer.common.base.annotation.SysLog;
import com.hqjy.transfer.common.base.base.AbstractController;
import com.hqjy.transfer.common.base.constant.StatusCode;
import com.hqjy.transfer.common.base.utils.PageInfo;
import com.hqjy.transfer.common.base.utils.PageQuery;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysClassEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysClassService;
import com.hqjy.transfer.crm.common.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author xieyuqing
 * @ description
 * @ date create in 2018/5/23 14:25
 */

@Api(tags = "班次管理", description = "SysClassController")
@RestController
@RequestMapping("/sys/class")
public class SysClassController extends AbstractController {


    @Autowired
    private SysClassService sysClassService;

    @ApiOperation(value = "分页查询-班次管理列表", notes = "这里不能进行接口测试，因为Swaggger参数不支持\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量],[sidx:排序字段],[sort:排序方式]\n" +
            "高级查询参数（RequestBody数据格式接收）：[className:班次名称] \n" +
            "示例： \n" +
            "分页参数：\n" +
            "pageNum=1 \n" +
            "pageSize=10 \n" +
            "sidx=classId \n" +
            "sort=desc \n" +
            "高级查询参数：\n" +
            "{\"className\":\"自考早班\"}\n" +
            "返回数据格式（【】内的为注释信息便于前端对接）: {\n" +
            "    \"【业务数据】result\": {\n" +
            "        \"【当前页】currPage\": 1,\n" +
            "        \"【当前页的数量】size\": 1,\n" +
            "        \"【当前页面最后一个元素在数据库中的行号】endRow\": 1,\n" +
            "        \"【当前页面第一个元素在数据库中的行号】startRow\": 1,\n" +
            "        \"【总记录数】totalCount\": 1,\n" +
            "        \"【总页数】totalPage\": 1,\n" +
            "        \"【每页的数量】pageSize\": 10,\n" +
            "        \"【结果集】list\": [\n" +
            "            {\n" +
            "                \"【创建人ID】createId\": 1,\n" +
            "                \"【创建时间】createTime\": \"2018-05-24 10:20:18\",\n" +
            "                \"【开始时间】startTime\": \"08:00:00\",\n" +
            "                \"【班次ID】classId\": 1,\n" +
            "                \"【班次名称】className\": \"自考早班\",\n" +
            "                \"【结束时间】stopTime\": \"10:00:00\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"【业务消息】msg\": \"成功\",\n" +
            "    \"【业务状态码】code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    @RequiresPermissions("sys:class:list")
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<SysClassEntity> classEntityPageInfo = new PageInfo<>(sysClassService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(classEntityPageInfo);
    }


    @ApiOperation(value = "获取班次下拉列表", notes = "获取班次下拉列表:下拉框列表只显示className值，隐藏classId:\n" +
            "响应参数格式例：：{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"classId\": 1,\n" +
            "      \"className\": \"早班\",\n" +
            "      \"startTime\": \"08:00:00\",\n" +
            "      \"stopTime\": \"10:00:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"classId\": 2,\n" +
            "      \"className\": \"中班\",\n" +
            "      \"startTime\": \"14:00:00\",\n" +
            "      \"stopTime\": \"18:00:00\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"code\": 0\n" +
            "}")
    @PostMapping("/getClassComboBox")
    //@RequiresPermissions("sys:class:comboBox")
    public R info() {
        List<SysClassEntity> list = sysClassService.getClassComboBox();
        if (list != null) {
            return R.ok(list);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    @ApiOperation(value = "新增班次管理", notes = "新增班次管理:输入参数列：{\n" +
            "  \"className\": \"早班\",\n" +
            "  \"startTime\": \"08:00:00\",\n" +
            "  \"stopTime\": \"10:00:00\"\n" +
            "}," +
            "保存成功响应数据格式：{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增班次管理")
    @PostMapping("/save")
    @RequiresPermissions("sys:class:save")
    public R save(@RequestBody SysClassEntity entity) {
        entity.setCreateId(ShiroUtils.getUserId());
        int save = sysClassService.save(entity);
        if (save > 0) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation(value = "修改班次管理", notes = "修改班次管理：请求参数如：{ \"classId\": 17,\n" +
            "\"className\": \"自考早班\",\n" +
            "\"startTime\": \"08:00:00\",\n" +
            "\"stopTime\": \"10:00:00\"" +
            "}，\n" +
            "更新成功响应参数：{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改班次管理")
    @PutMapping("/update")
    @RequiresPermissions("sys:class:update")
    public R update(@RequestBody SysClassEntity entity) {
        entity.setUpdateId(ShiroUtils.getUserId());
        int update = sysClassService.update(entity);
        if (update > 0) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation(value = "删除班次管理", notes = "删除班次管理：请求方式：/sys/class/{ids}\n" +
            "删除成功响应参数合适：{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("删除班次管理")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:class:delete")
    public R delete(@PathVariable("ids") Long[] id) {
        int delete = sysClassService.deleteBatch(id);
        return delete > 0 ? R.ok() : R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }


    @ApiOperation(value = "班次查重", notes = "删除班次管理：请求方式：/sys/class/findClassByName?className='自考早班'，\n" +
            "班次存在则响应格式：{\n" +
            "  \"msg\": \"失败\",\n" +
            "  \"code\": -1\n" +
            "}，" +
            "班次不存在响应：" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @GetMapping("/findClassByName")
    //@RequiresPermissions("sys:class:list")
    public R findClassByName(@RequestParam(value = "className") String className,
                             @RequestParam(value = "classId", required = false) Long classId) {

        return sysClassService.findClassByName(className, classId) == null ? R.ok() : R.error();
    }

}
