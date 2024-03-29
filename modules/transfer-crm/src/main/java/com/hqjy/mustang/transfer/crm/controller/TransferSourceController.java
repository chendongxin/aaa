package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
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
 * @date create in 2018年9月6日18:02:07
 */

@Api(tags = "推广管理-来源管理", description = "TransferSourceController")
@RestController
@RequestMapping("/source")
public class TransferSourceController {

    private TransferSourceService transferSourceService;

    @Autowired
    public void setTransferSourceService(TransferSourceService transferSourceService) {
        this.transferSourceService = transferSourceService;
    }

    /**
     * 来源平台管理树数据
     */
    @ApiOperation(value = "来源平台管理树数据", notes = "来源平台管理树数据，包含所有来源平台数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(transferSourceService.getRecursionTree(true));
    }

    /**
     * 分页查询来源信息
     */
    @ApiOperation(value = "分页查询-来源管理", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【ID:sourceId】,【父编号:parentId】,【来源名称:name】,【电子邮件域名:email_domain】【状态( 0-正常 1-禁用):status】,【创建人ID:createUserId】,【创建人姓名:createUserName】【创建时间:createTime】\n" +
            "【更新人ID:updateUserId】,【更新人姓名:updateUserName】,【更新时间:updateTime】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"sourceId\": 1,\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"status\": 0,\n" +
            "        \"createId\": 1,\n" +
            "        \"emailDomain\": \"jianli@zp.58.com\",\n" +
            "        \"createTime\": \"2018-09-06 17:19:24\",\n" +
            "        \"updateId\": 1,\n" +
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
        PageInfo<TransferSourceEntity> sourcePageInfo = new PageInfo<>(transferSourceService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(sourcePageInfo);
    }

    /**
     * 新增一条来源
     */
    @ApiOperation(value = "新增来源", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【来源平台名称:name】,【状态(0:正常; 1:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": 58,\n" +
            "  \"status\": 1,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增来源")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferSourceEntity transferSourceEntity) {
        int count = transferSourceService.save(transferSourceEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除来源
     */
    @ApiOperation(value = "删除来源", notes = "删除一条来源：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "sourceId", value = "来源ID", required = true, dataType = "Long")
    @SysLog("删除来源")
    @DeleteMapping("/{sourceId}")
    public R delete(@PathVariable("sourceId") Long sourceId) {
        int count = transferSourceService.delete(sourceId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改一条来源
     */
    @ApiOperation(value = "修改来源", notes = "修改一条来源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "sourceId", value = "来源ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "transferSourceEntity", value = "来源实体", required = true, dataType = "TransferSourceEntity")
    })
    @SysLog("修改来源")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferSourceEntity transferSourceEntity) {
        int count = transferSourceService.update(transferSourceEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 查询一条来源信息
     */
    @ApiOperation(value = "来源信息", notes = "来源信息: /info/1")
    @ApiImplicitParam(paramType = "path", name = "sourceId", value = "来源ID", required = true, dataType = "Long")
    @SysLog("查询来源")
    @GetMapping("/{sourceId}")
    public R info(@PathVariable("sourceId") Long sourceId) {
        TransferSourceEntity transferSourceEntity = transferSourceService.findOne(sourceId);
        if (transferSourceEntity != null) {
            return R.ok(transferSourceEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 获取所有的来源平台
     */
    @ApiOperation(value = "获取所有的来源平台", notes = "获取所有的来源平台")
    @GetMapping("/all")
    public R getAllSource() {
        return R.ok(transferSourceService.getAllSourceList());
    }

}
