package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCostEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenCostService;
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
 * @date create in 2018年9月7日17:51:07
 */

@Api(tags = "推广管理-推广费用", description = "TransferGenCostController")
@RestController
@RequestMapping("/gen/cost")
public class TransferGenCostController {

    private TransferGenCostService transferGenCostService;

    @Autowired
    public void setTransferGenCostService(TransferGenCostService transferGenCostService) {
        this.transferGenCostService = transferGenCostService;
    }

    /**
     * 分页查询推广费用
     */
    @ApiOperation(value = "分页查询-推广费用", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【编号:id】,【赛道ID:proId】,【推广公司ID:companyId】,【推广公司名称:name】,【部门ID:deptId】,【部门名称:deptName】,【来源平台ID:sourceId】\n" +
            "【推广方式ID:wayId】,【推广方式名称:genWay】,【推广日期:genDay】,【人民币主动:initiativeMoney】,【人民币被动:passiveMoney】,【虚拟币主动:initiativeVirtual】\n" +
            "【虚拟币被动:passiveVirtual】,【创建人ID:createUserId】,【创建人名称:createUserName】,【创建时间:createTime】\n" +
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
            "        \"proId\": 1,\n" +
            "        \"companyId\": 1,\n" +
            "        \"name\": \"广州百单网网络科技有限公司\",\n" +
            "        \"deptId\": 1,\n" +
            "        \"deptName\": \"广西校区\",\n" +
            "        \"sourceId\": 1,\n" +
            "        \"wayId\": 1,\n" +
            "        \"genWay\": \"精品帮帮\",\n" +
            "        \"genDay\": \"2018-09-06 17:19:24\",\n" +
            "        \"initiativeMoney\": 1000,\n" +
            "        \"passiveMoney\": 800,\n" +
            "        \"initiativeVirtual\": 100,\n" +
            "        \"passiveVirtual\": 100,\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"灵儿\",\n" +
            "        \"createTime\": \"2018-09-06 17:19:24\",\n" +
            "        \"updateUserId\": 1,\n" +
            "        \"updateUserName\": \"灵儿\",\n" +
            "        \"updateTime\": \"2018-09-06 17:19:24\",\n" +
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
        PageInfo<TransferGenCostEntity> genCostPageInfo = new PageInfo<>(transferGenCostService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(genCostPageInfo);
    }

    /**
     * 新增推广费用
     */
    @ApiOperation(value = "新增推广费用", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【赛道ID:proId】,【推广公司ID:companyId】,【部门ID:deptId】,【推广来源ID:sourceId】,【推广方式ID:wayId】\n" +
            "【推广时间:genDay】,【人民币主动:initiativeMoney】,【人民币被动:passiveMoney】,【虚拟币主动:initiativeVirtual】\n" +
            "【虚拟币被动:passiveVirtual】,【创建人ID:createUserId】,【创建人名称:createUserName】,【创建时间:createTime】\n" +
            "【更新人ID:updateUserId】,【更新人名称:updateUserName】,【更新时间:updateTime】\n" +
            "示例：\n" +
            "{\n" +
            "        \"proId\": 1,\n" +
            "        \"companyId\": 1,\n" +
            "        \"deptId\": 1,\n" +
            "        \"sourceId\": 1,\n" +
            "        \"wayId\": 1,\n" +
            "        \"genDay\": \"2018-09-06 17:19:24\",\n" +
            "        \"initiativeMoney\": 1000,\n" +
            "        \"passiveMoney\": 800,\n" +
            "        \"initiativeVirtual\": 100,\n" +
            "        \"passiveVirtual\": 100,\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"灵儿\",\n" +
            "        \"createTime\": \"2018-09-06 17:19:24\",\n" +
            "        \"updateUserId\": 1,\n" +
            "        \"updateUserName\": \"灵儿\",\n" +
            "        \"updateTime\": \"2018-09-06 17:19:24\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @ApiImplicitParam(paramType = "body", name = "TransferGenCostEntity", value = "推广费用信息对象", required = true, dataType = "TransferGenCostEntity")
    @SysLog("新增推广费用")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferGenCostEntity transferGenCostEntity) {
        int count = transferGenCostService.save(transferGenCostEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除推广费用
     */
    @ApiOperation(value = "删除推广费用", notes = "删除推广费用：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "推广费用ID", required = true, dataType = "Long")
    @SysLog("删除推广费用")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") Long[] ids) {
        int count = transferGenCostService.deleteBatch(ids);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改推广费用
     */
    @ApiOperation(value = "修改推广费用", notes = "修改推广费用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "推广费用ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "body", name = "transferGenCostEntity", value = "推广费用实体", required = true, dataType = "TransferGenCostEntity")
    })
    @SysLog("修改推广费用")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferGenCostEntity transferGenCostEntity) {
        int count = transferGenCostService.update(transferGenCostEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 查询一个推广费用
     */
    @ApiOperation(value = "推广费用信息", notes = "推广费用信息: /info/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "推广费用ID", required = true, dataType = "Long")
    @SysLog("查询一个推广费用")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        TransferGenCostEntity transferGenCostEntity = transferGenCostService.findOne(id);
        if (transferGenCostEntity != null) {
            return R.ok(transferGenCostEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }
}
