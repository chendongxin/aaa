package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
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
 * @date create in 2018年9月7日17:51:07
 */

@Api(tags = "推广管理-推广方式", description = "TransferGenWayController")
@RestController
@RequestMapping("/gen/way")
public class TransferGenWayController {

    @Autowired
    private TransferGenWayService transferGenWayService;

    /**
     * 推广方式管理树数据
     */
    @ApiOperation(value = "推广方式管理树数据", notes = "推广方式管理树数据，包含所有推广方式数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(transferGenWayService.getRecursionTree(true));
    }

    /**
     * 分页查询推广方式
     */
    @ApiOperation(value = "分页查询-推广方式", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【编号:wayId】,【父编号:parentId】,【来源ID:sourceId】,【推广方式:genWay】,【排序号:seq】,【状态(1:正常; 0:禁用):status】\n" +
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
            "        \"wayId\": 1,\n" +
            "        \"parentId\": 0,\n" +
            "        \"sourceId\": 1,\n" +
            "        \"genWay\": \"灵儿\",\n" +
            "        \"seq\": 1,\n" +
            "        \"status\": 1,\n" +
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
        PageInfo<TransferGenWayEntity> genWayPageInfo = new PageInfo<>(transferGenWayService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(genWayPageInfo);
    }

    /**
     * 新增推广方式
     */
    @ApiOperation(value = "新增推广方式", notes = "请求参数：\n" +
            "参数说明：\n" +
            " 【父编号:parentId】,【推广平台Id:sourceId】,【推广方式名称:genWay】,【排序号:seq】,【状态:status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"genWay\": \"精品帮帮\",\n" +
            "  \"parentId\": 2,\n" +
            "  \"sourceId\": 1,\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增推广方式")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferGenWayEntity transferGenWayEntity) {
        int count = transferGenWayService.save(transferGenWayEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除推广方式
     */
    @ApiOperation(value = "删除推广方式", notes = "删除推广方式：/delete/1")
        @ApiImplicitParam(paramType = "path", name = "wayId", value = "推广方式ID", required = true, dataType = "Long")
    @SysLog("删除推广方式")
    @DeleteMapping("/{wayIds}")
    public R delete(@PathVariable("wayIds") Long[] wayIds) {
        int count = transferGenWayService.deleteBatch(wayIds);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改推广方式
     */
    @ApiOperation(value = "修改推广方式", notes = "输入参数：\n" +
            "参数说明：\n" +
            " 【推广方式名称:genWay】,【推广平台Id:sourceId】,【排序号:seq】, 【状态( 1:正常; 0:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"genWay\": \"精品帮帮\",\n" +
            "  \"parentId\": 2,\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改推广方式")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferGenWayEntity transferGenWayEntity) {
        int count = transferGenWayService.update(transferGenWayEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 查询一个推广方式
     */
    @ApiOperation(value = "推广方式信息", notes = "推广方式信息: /info/1")
    @ApiImplicitParam(paramType = "path", name = "wayId", value = "推广方式ID", required = true, dataType = "Long")
    @SysLog("查询一个推广方式")
    @GetMapping("/{wayId}")
    public R info(@PathVariable("wayId") Long wayId) {
        TransferGenWayEntity transferGenWayEntity = transferGenWayService.findOne(wayId);
        if (transferGenWayEntity != null) {
            return R.ok(transferGenWayEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }
}
