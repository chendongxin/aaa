package com.hqjy.mustang.transfer.sms.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsSignatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:45
 */
@Api(tags = "短信签名管理", description = "TarnsferSmsSignatureController")
@RestController
@RequestMapping("/signature")
public class TarnsferSmsSignatureController {

    @Autowired
    private TransferSmsSignatureService transferSmsSignatureService;

    /**
     * 根据部门编号获取签名列表
     */
    @ApiOperation(value = "根据部门编号获取签名列表", notes = "/getListByDeptId/{deptId}\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"createTime\": \"2018-09-29 11:46:22\",\n" +
            "            \"createUserId\": 1,\n" +
            "            \"createUserName\": \"admin\",\n" +
            "            \"deptId\": 1,\n" +
            "            \"deptName\": \"野马部门\",\n" +
            "            \"id\": 1,\n" +
            "            \"signature\": \"【恒企教育】\",\n" +
            "            \"updateTime\": \"2018-09-29 14:25:21\",\n" +
            "            \"updateUserId\": 1,\n" +
            "            \"updateUserName\": \"admin\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": \"2018-09-29 11:46:22\",\n" +
            "            \"createUserId\": 1,\n" +
            "            \"createUserName\": \"admin\",\n" +
            "            \"deptId\": 1,\n" +
            "            \"deptName\": \"野马部门\",\n" +
            "            \"id\": 2,\n" +
            "            \"signature\": \"【百单网】\",\n" +
            "            \"updateTime\": \"2018-09-29 14:25:28\",\n" +
            "            \"updateUserId\": 1,\n" +
            "            \"updateUserName\": \"admin\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"code\": 0\n" +
            "}")
    @GetMapping("/getListByDeptId/{deptId}")
    public R getListByDeptId(@PathVariable("deptId") Long deptId) {
        return R.ok(transferSmsSignatureService.getListByDeptId(deptId));
    }

    /**
     * 签名信息
     */
    @ApiOperation(value = "签名信息", notes = "{\n" +
            "        \"[创建时间] createTime\": \"2018-09-29 11:46:22\",\n" +
            "        \"[创建用户ID] createUserId\": 1,\n" +
            "        \"[创建用户名称] createUserName\": \"admin\",\n" +
            "        \"[部门ID] deptId\": 0,\n" +
            "        \"[部门名称] deptName\": \"野马部门\",\n" +
            "        \"[签名ID] id\": 1,\n" +
            "        \"[签名信息] signature\": \"【签名信息】\",\n" +
            "        \"[更新时间] updateTime\": \"2018-09-29 11:52:15\",\n" +
            "        \"[更新用户ID] updateUserId\": 1,\n" +
            "        \"[更新用户名称] updateUserName\": \"admin\"\n" +
            "    }")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        TransferSmsSignatureEntity sms = transferSmsSignatureService.findOne(id);
        if (sms != null) {
            return R.ok(sms);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 分页查询签名信息
     */
    @ApiOperation(value = "分页查询签名信息", notes = "支持分页，排序和高级查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[创建时间] createTime\": \"2018-09-29 11:46:22\",\n" +
            "                \"[创建用户ID] createUserId\": 1,\n" +
            "                \"[创建用户名称] createUserName\": \"admin\",\n" +
            "                \"[部门ID] deptId\": 0,\n" +
            "                \"[部门名称] deptName\": \"野马部门\",\n" +
            "                \"[签名ID] id\": 1,\n" +
            "                \"[签名信息] signature\": \"【签名信息】\",\n" +
            "                \"[更新时间] updateTime\": \"2018-09-29 11:52:15\",\n" +
            "                \"[更新用户ID] updateUserId\": 1,\n" +
            "                \"[更新用户名称] updateUserName\": \"admin\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 10,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 1,\n" +
            "        \"totalPage\": 1\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferSmsSignatureEntity> userPageInfo = new PageInfo<>(transferSmsSignatureService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 签名保存
     */
    @ApiOperation(value = "增加签名", notes = "必要参数:\n" +
            "{\n" +
            "   \"deptId\": 0,\n" +
            "   \"deptName\": \"野马部门\",\n" +
            "   \"signature\": \"【签名信息】\",\n" +
            " }")
    @ApiImplicitParam(paramType = "body", name = "user", value = "签名信息对象", required = true, dataType = "TransferSmsSignatureEntity")
    @SysLog("保存签名")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferSmsSignatureEntity signatureEntity) {
        int count = transferSmsSignatureService.save(signatureEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除签名
     */
    @ApiOperation(value = "删除签名", notes = "删除签名：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "签名ID", required = true, dataType = "String")
    @SysLog("删除签名")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        int count = transferSmsSignatureService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改签名
     */
    @ApiOperation(value = "更新签名信息", notes = "更新签名详细信息:修改ID不能为空\n" +
            "{\n" +
            "   \"id\": 1,\n" +
            "   \"deptId\": 0,\n" +
            "   \"deptName\": \"野马部门\",\n" +
            "   \"signature\": \"【签名信息】\",\n" +
            " }")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", value = "签名详细实体", required = true, dataType = "TransferSmsSignatureEntity"),
    })
    @SysLog("修改签名")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferSmsSignatureEntity user) {
        int count = transferSmsSignatureService.update(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

}
