package com.hqjy.mustang.transfer.sms.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsService;
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
@Api(tags = "短信管理", description = "TarnsferSmsController")
@RestController
@RequestMapping("/sms")
public class TarnsferSmsController {

    @Autowired
    private TransferSmsService transferSmsService;

    /**
     * 短信执行发送
     */
    @ApiOperation(value = "短信执行发送", notes = "{ids}[短信编号]\n/send/1,2,3")
    @PostMapping("/send/{ids}")
    @SysLog("短信执行发送")
    public R smsSend(@PathVariable("ids") Long[] ids) {
        int count = transferSmsService.smsSend(ids);
        if (count > 0) {
            return R.ok("成功提交" + count + "条，请等待发送结果");
        }
        return R.error();
    }

    /**
     * 获取一个短信信息
     */
    @ApiOperation(value = "获取一个短信信息", notes = "/11\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"[短信ID] id\": 21,\n" +
            "        \"[部门ID] deptId\": 1,\n" +
            "        \"[部门名称] deptName\": \"野马部门\",\n" +
            "        \"[短信接收时间] doneTime\": \"2018-09-29 12:11:06\",\n" +
            "        \"[短信内容] content\": \"【Lolipop】您好嘛，HejinYo。2018-09-29 11:14\",\n" +
            "        \"[收件人] phone\": \"13265115030\",\n" +
            "        \"[提交发送时间] sendTime\": \"2018-09-29 12:10:48\",\n" +
            "        \"[状态码] status\": 2,\n" +
            "        \"[状态显示] statusValue\": \"发送成功\",\n" +
            "        \"[短信发送时间] submitTime\": \"2018-09-29 12:11:00\",\n" +
            "        \"[创建时间] createTime\": \"2018-09-29 12:10:41\",\n" +
            "        \"[创建用户ID] createUserId\": 1,\n" +
            "        \"[创建用户名称] createUserName\": \"admin\",\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        TransferSmsEntity sms = transferSmsService.findOne(id);
        if (sms != null) {
            return R.ok(sms);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 分页查询短信信息
     */
    @ApiOperation(value = "分页查询短信信息", notes = "支持分页，排序和高级查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 10,\n" +
            "        \"endRow\": 20,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "             \"[短信ID] id\": 21,\n" +
            "             \"[部门ID] deptId\": 1,\n" +
            "             \"[部门名称] deptName\": \"野马部门\",\n" +
            "             \"[短信接收时间] doneTime\": \"2018-09-29 12:11:06\",\n" +
            "             \"[短信内容] content\": \"【Lolipop】您好嘛，HejinYo。2018-09-29 11:14\",\n" +
            "             \"[收件人] phone\": \"13265115030\",\n" +
            "             \"[提交发送时间] sendTime\": \"2018-09-29 12:10:48\",\n" +
            "             \"[状态码] status\": 2,\n" +
            "             \"[状态显示] statusValue\": \"发送成功\",\n" +
            "             \"[短信发送时间] submitTime\": \"2018-09-29 12:11:00\",\n" +
            "             \"[创建时间] createTime\": \"2018-09-29 12:10:41\",\n" +
            "             \"[创建用户ID] createUserId\": 1,\n" +
            "             \"[创建用户名称] createUserName\": \"admin\",\n" +
            "            },\n" +
            "        ],\n" +
            "        \"pageSize\": 2,\n" +
            "        \"size\": 2,\n" +
            "        \"startRow\": 19,\n" +
            "        \"totalCount\": 20,\n" +
            "        \"totalPage\": 10\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferSmsEntity> userPageInfo = new PageInfo<>(transferSmsService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 短信保存
     */
    @ApiOperation(value = "增加短信", notes = "增加短信\n" +
            "{\n" +
            "    \"[部门ID] dept_id\":\"1\",\n" +
            "    \"[部门名称] dept_name\":\"野马部门\",\n" +
            "    \"[收件人] phone\":\"17600222250,13265115030\",\n" +
            "    \"[短信内容] content\":\"【Lolipop】正式环境启用，HejinYo。2018-09-29 11:14\"\n" +
            "}")
    @ApiImplicitParam(paramType = "body", name = "user", value = "短信信息对象", required = true, dataType = "TransferSmsEntity")
    @SysLog("保存短信")
    @PostMapping("/batch")
    public R saveSms(@Validated(RestfulValid.POST.class) @RequestBody TransferSmsEntity smsEntity) {
        int count = transferSmsService.saveSms(smsEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除短信
     */
    @ApiOperation(value = "删除短信", notes = "删除短信：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "id", value = "短信ID", required = true, dataType = "String")
    @SysLog("删除短信")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        int count = transferSmsService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改短信
     */
    @ApiOperation(value = "更新短信信息", notes = "更新短信详细信息\n" +
            "{\n" +
            "    \"[部门ID] dept_id\":\"1\",\n" +
            "    \"[部门名称] dept_name\":\"野马部门\",\n" +
            "    \"[收件人] phone\":\"17600222250,13265115030\",\n" +
            "    \"[短信内容] content\":\"【Lolipop】正式环境启用，HejinYo。2018-09-29 11:14\"\n" +
            "}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", value = "短信详细实体", required = true, dataType = "TransferSmsEntity"),
    })
    @SysLog("修改短信")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferSmsEntity user) {
        int count = transferSmsService.update(user);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }
}
