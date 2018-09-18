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
 * @description
 * @date create in 2018年9月7日17:51:07
 */

@Api(tags = "推广管理-推广方式", description = "TransferGenWayController")
@RestController
@RequestMapping("/gen/way")
public class TransferGenWayController {

    @Autowired
    private TransferGenWayService transferGenWayService;
    @Autowired
    private TransferSourceService transferSourceService;
    @Autowired
    private TransferWaySourceService transferWaySourceService;

    /**
     * 分页查询来源平台
     */
    @ApiOperation(value = "分页查询-来源平台", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【来源ID:sourceId】,【父编号:parentId】,【来源平台名称:name】,【电子邮件域名:emailDomain】,【状态(0 : 正常 1 : 禁用):status】\n" +
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
            "        \"sourceId\": 1,\n" +
            "        \"parentId\": 0,\n" +
            "        \"name\": \"58\",\n" +
            "        \"emailDomain\": \"152954@163.com\",\n" +
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
    @RequestMapping(value = "/source/listPage",method = {RequestMethod.POST,RequestMethod.GET})
    public R listPage(@RequestParam HashMap<String, Object> pageParam,
                      @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferSourceEntity> sourcePageInfo = new PageInfo<>(transferSourceService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(sourcePageInfo);
    }

    /**
     * 新增推广方式
     */
    @ApiOperation(value = "新增推广方式", notes = "请求参数：\n" +
            "参数说明：\n" +
            " 【推广平台Id:sourceId】,【推广方式名称:genWay】,【排序号:seq】,【状态:status】\n" +
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
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") Long[] ids) {
        int count = transferWaySourceService.deleteBatch(ids);
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
        int count = transferWaySourceService.updateWaySource(transferGenWaySourceDTO);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    @ApiOperation(value = "分页查询-推广方式", notes = "请求参数：\n" +
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
    @RequestMapping(value = "/listPage",method = {RequestMethod.POST,RequestMethod.GET})
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferWaySourceEntity> waySourcePageInfo = new PageInfo<>(transferWaySourceService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(waySourcePageInfo);
    }

//    /**
//     * 获取不属于指定平台的推广方式
//     */
//    @GetMapping("/get/{sourceId}")
//    @ApiOperation(value = "获取推广平台接口", notes = "请求参数说明")
//    public R listPage(@PathVariable("sourceId") Long sourceId) {
//        return R.ok(transferGenWayService.findNotBySourceId(sourceId));
//    }

//    /**
//     * 查询一个推广方式
//     */
//    @ApiOperation(value = "推广方式信息", notes = "推广方式信息: /info/1")
//    @ApiImplicitParam(paramType = "path", name = "wayId", value = "推广方式ID", required = true, dataType = "Long")
//    @SysLog("查询一个推广方式")
//    @GetMapping("/{wayId}")
//    public R info(@PathVariable("wayId") Long wayId) {
//        TransferGenWayEntity transferGenWayEntity = transferGenWayService.findOne(wayId);
//        if (transferGenWayEntity != null) {
//            return R.ok(transferGenWayEntity);
//        }
//        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
//    }


}
