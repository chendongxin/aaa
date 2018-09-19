package com.hqjy.mustang.transfer.crawler.controller;

import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferEmailEntity;
import com.hqjy.mustang.transfer.crawler.service.TrasferEmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 邮箱配置
 *
 * @author : heshuangshuang
 * @date : 2018/9/11 9:38
 */
@RestController
@RequestMapping("/email")
@Api(tags = "邮箱管理", description = "TrasferEmailController")
public class TrasferEmailController {

    @Autowired
    private TrasferEmailService trasferEmailService;

    /**
     * 邮箱配置信息
     */
    @ApiOperation(value = "邮箱配置信息", notes = "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"companyId\": 1,\n" +
            "        \"companyName\": \"恒企教育\",\n" +
            "        \"createTime\": \"2018-09-12 15:31:58\",\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"admin\",\n" +
            "        \"deptId\": 8000,\n" +
            "        \"deptName\": \"野马部门\",\n" +
            "        \"email\": \"hr12@baidanwang.cn\",\n" +
            "        \"id\": 1,\n" +
            "        \"password\": \"7n6unsKPCM1\",\n" +
            "        \"proId\": 1,\n" +
            "        \"proName\": \"IT赛道\",\n" +
            "        \"status\": 0,\n" +
            "        \"updateTime\": \"2018-09-13 09:15:11\",\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"admin\"\n" +
            "    },\n" +
            "    \"code\": 0\n" +
            "}")
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id) {
        TransferEmailEntity roleEntity = trasferEmailService.findOne(id);
        if (roleEntity != null) {
            return R.ok(roleEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }

    /**
     * 邮箱配置分页查询
     */
    @ApiOperation(value = "邮箱配置分页查询", notes = "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"result\": {\n" +
            "        \"currPage\": 1,\n" +
            "        \"endRow\": 1,\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"companyId\": 1,\n" +
            "                \"companyName\": \"恒企教育\",\n" +
            "                \"createTime\": \"2018-09-12 15:31:58\",\n" +
            "                \"createUserId\": 1,\n" +
            "                \"createUserName\": \"admin\",\n" +
            "                \"deptId\": 8000,\n" +
            "                \"deptName\": \"野马部门\",\n" +
            "                \"email\": \"hr12@baidanwang.cn\",\n" +
            "                \"id\": 1,\n" +
            "                \"password\": \"7n6unsKPCM1\",\n" +
            "                \"proId\": 1,\n" +
            "                \"proName\": \"IT赛道\",\n" +
            "                \"status\": 0,\n" +
            "                \"updateTime\": \"2018-09-13 09:15:11\",\n" +
            "                \"userId\": 1,\n" +
            "                \"userName\": \"admin\"\n" +
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
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<TransferEmailEntity> userRoleInfo = new PageInfo<>(trasferEmailService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(userRoleInfo);
    }

    /**
     * 保存邮箱配置
     */
    @ApiOperation(value = "保存邮箱配置", notes = "都是必须填写的参数：\n{\n" +
            "        \"companyId\": 1,\n" +
            "        \"companyName\": \"恒企教育\",\n" +
            "        \"deptId\": 8000,\n" +
            "        \"deptName\": \"野马部门\",\n" +
            "        \"email\": \"hr12@baidanwang.cn\",\n" +
            "        \"password\": \"7n6unsKPCM1\",\n" +
            "        \"proId\": 1,\n" +
            "        \"proName\": \"IT赛道\",\n" +
            "        \"status\": 0,\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"admin\"\n" +
            "    }")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferEmailEntity role) {
        int count = trasferEmailService.save(role);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 修改邮箱配置
     */
    @ApiOperation(value = "修改邮箱配置", notes = "id必须有才能修改：\n{\n" +
            "        \"id\": 1,\n" +
            "        \"companyId\": 1,\n" +
            "        \"companyName\": \"恒企教育\",\n" +
            "        \"deptId\": 8000,\n" +
            "        \"deptName\": \"野马部门\",\n" +
            "        \"email\": \"hr12@baidanwang.cn\",\n" +
            "        \"password\": \"7n6unsKPCM1\",\n" +
            "        \"proId\": 1,\n" +
            "        \"proName\": \"IT赛道\",\n" +
            "        \"status\": 0,\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"admin\"\n" +
            "    }")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferEmailEntity role) {
        int count = trasferEmailService.update(role);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 删除邮箱配置
     */
    @ApiOperation(value = "删除邮箱配置", notes = "支持单个和多个删除，主键用逗号分割\n示例:\t/1,2,3")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        int count = trasferEmailService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

}
