package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.admin.service.SysProductService;
import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = "系统菜单-赛道管理", description = "SysProductController")
@RestController
@RequestMapping("/product")
public class SysProductController {

    @Autowired
    private SysProductService sysProductService;

    /**
     * 分页查询赛道
     */
    @ApiOperation(value = "分页查询-赛道", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量], [sidx:排序字段],[treeKey:查询字段],[treeValue:查询字段值]\n" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【赛道Id:proId】【赛道名称:name】,【状态(0 : 正常 1 : 禁用):status】,【创建人ID:createUserId】\n" +
            "【创建时间:createTime】,【更新人ID:updateUserId】,【更新时间:updateTime】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"proId\": 1,\n" +
            "        \"name\": \"IT赛道\",\n" +
            "        \"status\": 0,\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createTime\": \"2018-09-06 17:19:24\",\n" +
            "        \"updateUserId\": 1,\n" +
            "        \"updateName\": \"2018-09-06 17:19:24\",\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageSize\": 10,\n" +
            "    \"size\": 1,\n" +
            "    \"startRow\": 1,\n" +
            "    \"totalCount\": 1,\n" +
            "    \"totalPage\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage",method = {RequestMethod.POST,RequestMethod.GET})
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<SysProductEntity> sysProductPageInfo = new PageInfo<>(sysProductService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(sysProductPageInfo);
    }

    /**
     * 新增赛道
     */
    @ApiOperation(value = "新增赛道", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【赛道名称:name】,【备注:memo】,【状态(0:正常; 1:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"IT赛道\",\n" +
            "  \"status\": 0,\n" +
            "  \"memo\": \"赛道\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增赛道")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody SysProductEntity syaProductEntity) {
        int count = sysProductService.save(syaProductEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改赛道
     */
    @ApiOperation(value = "修改赛道", notes = "输入参数：\n" +
            "参数说明：\n" +
            "【赛道名称:name】,【备注:memo】,【状态(0:正常; 1:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"IT赛道\",\n" +
            "  \"status\": 0,\n" +
            "  \"memo\": \"赛道\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改推广公司")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody SysProductEntity syaProductEntity) {
        int count = sysProductService.update(syaProductEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除赛道
     */
    @ApiOperation(value = "删除赛道", notes = "删除赛道：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "proId", value = "赛道ID", required = true, dataType = "Long")
    @SysLog("删除赛道")
    @DeleteMapping("/{proIds}")
    public R delete(@PathVariable("proIds") Long[] proIds) {
        int count = sysProductService.deleteBatch(proIds);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

}
