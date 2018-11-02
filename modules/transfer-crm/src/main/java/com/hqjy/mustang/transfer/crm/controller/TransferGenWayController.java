package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
import com.hqjy.mustang.transfer.crm.service.TransferWaySourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author guomiaomiao
 * @date create in 2018年9月7日17:51:07
 */

@Api(tags = "推广管理-推广方式", description = "TransferGenWayController")
@RestController
@RequestMapping("/gen/way")
public class TransferGenWayController {

    private TransferGenWayService transferGenWayService;
    private TransferWaySourceService transferWaySourceService;

    @Autowired
    public void setTransferGenWayService(TransferGenWayService transferGenWayService) {
        this.transferGenWayService = transferGenWayService;
    }

    @Autowired
    public void setTransferWaySourceService(TransferWaySourceService transferWaySourceService) {
        this.transferWaySourceService = transferWaySourceService;
    }

    /**
     * 获取所有推广方式
     */
    @ApiOperation(value = "获取所有推广方式", notes = "获取所有推广方式，包含所有推广方式数据")
    @GetMapping("/get/all")
    public R getAllGenWay() {
        return R.ok(transferGenWayService.getAllGenWayList());
    }

    @ApiOperation(value = "分页查询-指定平台推广方式", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【推广方式编号:wayId】,【来源平台编号:sourceId】,【推广方式名称:genWay】,【来源平台名称:name】,【排序号:seq】,【状态(0 : 正常 1 : 禁用):status】\n" +
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
            "        \"sourceId\": 1,\n" +
            "        \"genWay\": \"灵儿\",\n" +
            "        \"name\": \"58\",\n" +
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
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferWaySourceEntity> waySourcePageInfo = new PageInfo<>(transferWaySourceService.findPageSource(PageQuery.build(pageParam, queryParam)));
        return R.ok(waySourcePageInfo);
    }

    /**
     * 新增推广平台下的推广方式
     */
    @ApiOperation(value = "新增推广平台下的推广方式", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【推广平台Id:sourceId】,【推广方式名称:genWay】,【排序号:seq】,【状态:status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"genWay\": \"精品帮帮\",\n" +
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
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferGenWaySourceDTO transferGenWaySourceDTO) {
        int count = transferWaySourceService.saveWaySource(transferGenWaySourceDTO);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除指定平台的推广方式
     */
    @ApiOperation(value = "删除指定平台的推广方式", notes = "删除指定平台的推广方式：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "wayId", value = "推广方式ID", required = true, dataType = "Long")
    @SysLog("删除指定平台的推广方式")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        int count = transferWaySourceService.delete(id);
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
            "【推广方式名称:genWay】,【推广平台Id:sourceId】,【排序号:seq】, 【状态( 1:正常; 0:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"genWay\": \"精品帮帮\",\n" +
            "  \"sourceId\": 2,\n" +
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
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferGenWaySourceDTO transferGenWaySourceDTO) {
        int count = transferGenWayService.update(transferGenWaySourceDTO);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 获取指定来源平台的推广方式
     */
    @ApiOperation(value = "获取指定来源平台的推广方式", notes = "获取指定来源平台的推广方式")
    @GetMapping("/source/{sourceId}")
    public R getWayBySourceId(@PathVariable("sourceId") Long sourceId) {
        return R.ok(transferGenWayService.findBySourceId(sourceId));
    }

    /**
     * 获取不属于指定来源平台的推广方式
     */
    @ApiOperation(value = "获取不属于指定来源平台的推广方式", notes = "获取不属于指定来源平台的推广方式")
    @GetMapping("/source/not/{sourceId}")
    public R listPage(@PathVariable("sourceId") Long sourceId) {
        return R.ok(transferGenWayService.findNotBySourceId(sourceId));
    }

}
