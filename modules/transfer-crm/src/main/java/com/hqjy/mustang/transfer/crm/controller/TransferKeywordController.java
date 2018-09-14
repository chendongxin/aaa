package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferKeywordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author guomiaomiao
 * @description
 * @date create in 2018年9月7日15:22:07
 */

@Api(tags = "推广管理-关键词配置", description = "TransferKeywordController")
@RestController
@RequestMapping("/keyword")
public class TransferKeywordController {

    @Autowired
    private TransferKeywordService transferKeywordService;

    /**
     * 关键词管理树数据
     */
    @ApiOperation(value = "关键词管理树数据", notes = "关键词管理树数据，包含所有关键词数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(transferKeywordService.getRecursionTree(true));
    }

    /**
     * 分页查询关键词
     */
    @ApiOperation(value = "分页查询-关键词配置", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【id:id】,【关联编号:parentId】,【名称:name】,【状态:status】,【属性级别(1：岗位级别 2：关键词):level】\n" +
            "【创建人ID:createUserId】,【创建人名称:createUserName】,【创建时间:createTime】\n" +
            "【更新人ID:updateUserId】,【更新人名称:updateUserName】,【更新时间:updateTime】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"parentId\": 1,\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"status\": 0,\n" +
            "        \"level\": 1,\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"灵儿\",\n" +
            "        \"createTime\": \"2018-09-06 17:19:24\",\n" +
            "        \"updateUserId\": 1,\n" +
            "        \"updateUserName\": \"灵儿\",\n" +
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
        PageInfo<TransferKeywordEntity> keywordPageInfo = new PageInfo<>(transferKeywordService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(keywordPageInfo);
    }

    /**
     * 新增关键词
     */
    @ApiOperation(value = "新增关键词", notes = "请求参数：\n" +
            "参数说明：\n" +
            " 【岗位类别(大类下添加)/关键词(小类下添加):name】,【根节点:parentId】,【排序号:seq】\n" +
            " 【状态(1:正常; 0:禁用):status】, 【属性级别(1: 岗位类别, 2: 关键词):level】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"测试类\",\n" +
            "  \"parentId\": 2,\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "  \"level\": 2,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增关键词")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferKeywordEntity transferKeywordEntity) {
        int count = transferKeywordService.save(transferKeywordEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除关键词
     */
    @ApiOperation(value = "删除关键词", notes = "删除关键词：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "关键词ID", required = true, dataType = "Integer")
    @SysLog("删除关键词")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") Integer[] ids) {
        int count = transferKeywordService.deleteBatch(ids);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改关键词
     */
    @ApiOperation(value = "修改关键词", notes = "输入参数：\n" +
            "参数说明：\n" +
            " 【岗位类别(大类下添加)/关键词(小类下添加):name】,【根节点:parentId】,【排序号:seq】\n" +
            " 【状态( 1:正常; 0:禁用):status】, 【属性级别(1: 岗位类别, 2: 关键词):level】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"测试类\",\n" +
            "  \"parentId\": 2,\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "  \"level\": 2,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改关键词")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferKeywordEntity transferKeywordEntity) {
        int count = transferKeywordService.update(transferKeywordEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 查询一个关键词
     */
    @ApiOperation(value = "关键词信息", notes = "关键词信息: /info/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "关键词ID", required = true, dataType = "Integer")
    @SysLog("查询一个关键词")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        TransferKeywordEntity transferKeywordEntity = transferKeywordService.findOne(id);
        if (transferKeywordEntity != null) {
            return R.ok(transferKeywordEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }
}
