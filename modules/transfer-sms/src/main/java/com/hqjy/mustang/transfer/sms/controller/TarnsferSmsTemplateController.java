package com.hqjy.mustang.transfer.sms.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsTemplateEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsTemplateService;
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
@Api(tags = "短信模版管理", description = "TarnsferSmsTemplateController")
@RestController
@RequestMapping("/template")
public class TarnsferSmsTemplateController {

    @Autowired
    private TransferSmsTemplateService transferSmsTemplateService;

    /**
     * 根据部门编号获取模版列表
     */
    @ApiOperation(value = "根据部门编号获取模版列表", notes = "/getListByDeptId/{deptId}\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": [\n" +
            "        {\n" +
            "            \"content\": \"模板内容\",\n" +
            "            \"createTime\": \"2018-09-29 14:09:28\",\n" +
            "            \"createUserId\": 1,\n" +
            "            \"createUserName\": \"admin\",\n" +
            "            \"deptId\": 1,\n" +
            "            \"deptName\": \"野马部门\",\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"模板1\",\n" +
            "            \"updateTime\": \"2018-09-29 14:09:28\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"模板内容${1}\",\n" +
            "            \"createTime\": \"2018-09-29 14:09:46\",\n" +
            "            \"createUserId\": 1,\n" +
            "            \"createUserName\": \"admin\",\n" +
            "            \"deptId\": 1,\n" +
            "            \"deptName\": \"野马部门\",\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"模板2\",\n" +
            "            \"updateTime\": \"2018-09-29 14:09:46\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"山西牵引力科技有限公司邀请您7月26日下午14:00-16:30到公司面议（#time#） 地址:小店区平阳路长风街平阳景苑六号楼14层13A11 线路:可乘坐13路,877路等公交到长风桥东下车即可 联系人:18535153576冯凯 微信号:Rudy-Yuki 收到请回复,谢谢！\",\n" +
            "            \"createTime\": \"2018-09-29 14:10:23\",\n" +
            "            \"createUserId\": 1,\n" +
            "            \"createUserName\": \"admin\",\n" +
            "            \"deptId\": 1,\n" +
            "            \"deptName\": \"野马部门\",\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"牵引力科技\",\n" +
            "            \"updateTime\": \"2018-09-29 14:10:23\",\n" +
            "            \"updateUserId\": 1,\n" +
            "            \"updateUserName\": \"admin\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"code\": 0\n" +
            "}")
    @GetMapping("/getListByDeptId/{deptId}")
    public R getListByDeptId(@PathVariable("deptId") Long deptId) {
        return R.ok(transferSmsTemplateService.getListByDeptId(deptId));
    }

    /**
     * 模版信息
     */
    @ApiOperation(value = "模版信息", notes = "模版信息\n" +
            "{\n" +
            "        \"[模板内容] content\": \"山西牵引力科技有限公司邀请您7月26日下午14:00-16:30到公司面议（#time#） 地址:小店区平阳路长风街平阳景苑六号楼14层13A11 线路:可乘坐13路,877路等公交到长风桥东下车即可 联系人:18535153576冯凯 微信号:Rudy-Yuki 收到请回复,谢谢！\",\n" +
            "        \"[创建时间] createTime\": \"2018-09-29 14:10:23\",\n" +
            "        \"[创建用户ID] createUserId\": 1,\n" +
            "        \"[创建用户名称] createUserName\": \"admin\",\n" +
            "        \"[部门ID] deptId\": 1,\n" +
            "        \"[部门名称] deptName\": \"野马部门\",\n" +
            "        \"[模板ID] id\": 3,\n" +
            "        \"[模板名称] name\": \"牵引力科技\",\n" +
            "        \"[更新时间] updateTime\": \"2018-09-29 14:10:23\",\n" +
            "        \"[更新用户ID] updateUserId\": 1,\n" +
            "        \"[更新用户名称] updateUserName\": \"admin\"" +
            "    }")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        TransferSmsTemplateEntity sms = transferSmsTemplateService.findOne(id);
        if (sms != null) {
            return R.ok(sms);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 分页查询模版信息
     */
    @ApiOperation(value = "分页查询模版信息", notes = "支持分页，排序和高级查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "        \"[模板内容] content\": \"山西牵引力科技有限公司邀请您7月26日下午14:00-16:30到公司面议（#time#） 地址:小店区平阳路长风街平阳景苑六号楼14层13A11 线路:可乘坐13路,877路等公交到长风桥东下车即可 联系人:18535153576冯凯 微信号:Rudy-Yuki 收到请回复,谢谢！\",\n" +
            "        \"[创建时间] createTime\": \"2018-09-29 14:10:23\",\n" +
            "        \"[创建用户ID] createUserId\": 1,\n" +
            "        \"[创建用户名称] createUserName\": \"admin\",\n" +
            "        \"[部门ID] deptId\": 1,\n" +
            "        \"[部门名称] deptName\": \"野马部门\",\n" +
            "        \"[模板ID] id\": 3,\n" +
            "        \"[模板名称] name\": \"牵引力科技\",\n" +
            "        \"[更新时间] updateTime\": \"2018-09-29 14:10:23\",\n" +
            "        \"[更新用户ID] updateUserId\": 1,\n" +
            "        \"[更新用户名称] updateUserName\": \"admin\"" +
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
        PageInfo<TransferSmsTemplateEntity> userPageInfo = new PageInfo<>(transferSmsTemplateService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 模版保存
     */
    @ApiOperation(value = "增加模版", notes = "增加模版,必要参数\n" +
            "{\n" +
            "      \"content\": \"山西牵引力科技有限公司邀请您7月26日下午14:00-16:30到公司面议（#time#） 地址:小店区平阳路长风街平阳景苑六号楼14层13A11 线路:可乘坐13路,877路等公交到长风桥东下车即可 联系人:18535153576冯凯 微信号:Rudy-Yuki 收到请回复,谢谢！\",\n" +
            "      \"deptId\": 1,\n" +
            "      \"deptName\": \"野马部门\",\n" +
            "      \"name\": \"牵引力科技\",\n" +
            "}")
    @ApiImplicitParam(paramType = "body", name = "user", value = "模版信息对象", required = true, dataType = "TransferSmsTemplateEntity")
    @SysLog("保存模版")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferSmsTemplateEntity templateEntity) {
        int count = transferSmsTemplateService.save(templateEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除模版
     */
    @ApiOperation(value = "删除模版", notes = "删除模版：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "模版ID", required = true, dataType = "String")
    @SysLog("删除模版")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        int count = transferSmsTemplateService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改模版
     */
    @ApiOperation(value = "更新模版信息", notes = "更新模版详细信息:修改ID不能为空\n" +
            "{\n" +
            "        \"[模板ID] id\": 3,\n" +
            "        \"[模板内容] content\": \"山西牵引力科技有限公司邀请您7月26日下午14:00-16:30到公司面议（#time#） 地址:小店区平阳路长风街平阳景苑六号楼14层13A11 线路:可乘坐13路,877路等公交到长风桥东下车即可 联系人:18535153576冯凯 微信号:Rudy-Yuki 收到请回复,谢谢！\",\n" +
            "        \"[部门ID] deptId\": 1,\n" +
            "        \"[部门名称] deptName\": \"野马部门\",\n" +
            "        \"[模板名称] name\": \"牵引力科技\",\n" +
            "    }")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", value = "模版详细实体", required = true, dataType = "TransferSmsTemplateEntity"),
    })
    @SysLog("修改模版")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferSmsTemplateEntity user) {
        int count = transferSmsTemplateService.update(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }
}
