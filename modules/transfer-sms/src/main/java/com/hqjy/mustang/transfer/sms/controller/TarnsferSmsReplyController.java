package com.hqjy.mustang.transfer.sms.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsReplyEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsReplyService;
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
@Api(tags = "短信回复管理", description = "TarnsferSmsReplyController")
@RestController
@RequestMapping("/reply")
public class TarnsferSmsReplyController {

    private final TransferSmsReplyService transferSmsReplyService;

    @Autowired
    public TarnsferSmsReplyController(TransferSmsReplyService transferSmsReplyService) {
        this.transferSmsReplyService = transferSmsReplyService;
    }

    /**
     * 回复短信
     */
    @ApiOperation(value = "回复短信", notes = "示例：/send/1 {\"content\": \"你好\"}\n" +
            "路径参数：[回复短息ID] {id}\n" +
            "body参数：[回复内容]{\"content\": \"你好\"}")
    @PostMapping("/sendTo/{id}")
    @SysLog("回复短信")
    public R smsReply(@PathVariable("id") Long id, @Validated(RestfulValid.DELETE.class) @RequestBody TransferSmsEntity smsEntity) {
        int count = transferSmsReplyService.smsReply(id, smsEntity);
        if (count > 0) {
            return R.ok("回复成功");
        }
        return R.error();
    }

    /**
     * 分页查询短信回复信息
     */
    @ApiOperation(value = "分页查询短信回复信息", notes = "支持分页，排序和高级查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[信息内容] content\": \"测试内容\",\n" +
            "                \"[部门ID] deptId\": 1,\n" +
            "                \"[部门名称] deptName\": \"野马部门\",\n" +
            "                \"[回复ID] id\": 1,\n" +
            "                \"[用户姓名] name\": \"王二\",\n" +
            "                \"[发信手机] phone\": \"17600222250\",\n" +
            "                \"[状态：0 未回复，1 已回复] status\": 0,\n" +
            "                \"[发送时间] submitTime\": \"2018-09-30 14:14:17\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 1,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 1,\n" +
            "        \"totalPage\": 1\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R postListPage(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferSmsReplyEntity> userPageInfo = new PageInfo<>(transferSmsReplyService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userPageInfo);
    }

    /**
     * 获取短信回复信息
     */
    @ApiOperation(value = "获取短信回复信息", notes = "示例：1?pageSize=1&sidx=sendTime&sort=desc\n" +
            "参数部门只能是pageSize,pageNum,sidx,sort只支持分页和排序，不支持查询\n" +
            "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"[发送内容] content\": \"【恒企教育】正式环境启用，jqjy。2018-09-29 17:29\",\n" +
            "                \"[短信创建时间] createTime\": \"2018-09-29 17:30:00\",\n" +
            "                \"[发送人ID] createUserId\": 1,\n" +
            "                \"[发送人名称] createUserName\": \"admin\",\n" +
            "                \"[部门ID] deptId\": 1,\n" +
            "                \"[部门名称] deptName\": \"野马部门\",\n" +
            "                \"[接收时间] doneTime\": \"2018-09-29 17:36:45\",\n" +
            "                \"[短信ID] id\": 26,\n" +
            "                \"[收件手机] phone\": \"17600222250\",\n" +
            "                \"[提交发送时间] sendTime\": \"2018-09-29 17:32:41\",\n" +
            "                \"[发送状态码] status\": 2,\n" +
            "                \"[发送状态] statusValue\": \"发送成功\",\n" +
            "                \"[发送成功时间] submitTime\": \"2018-09-29 17:36:39\",\n" +
            "            }\n" +
            "        ],\n" +
            "        \"pageSize\": 1,\n" +
            "        \"size\": 1,\n" +
            "        \"startRow\": 1,\n" +
            "        \"totalCount\": 11,\n" +
            "        \"totalPage\": 11\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @GetMapping("/{id}")
    public R replyList(@PathVariable("id") Long id, @RequestParam HashMap<String, Object> pageParam) {
        PageInfo<TransferSmsEntity> transferSmsEntityPageInfo = new PageInfo<>(transferSmsReplyService.replyList(id, PageQuery.build(pageParam)));
        return R.ok(transferSmsEntityPageInfo);
    }
}
