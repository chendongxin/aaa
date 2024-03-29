package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCompanySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCompanyEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCompanySourceService;
import com.hqjy.mustang.transfer.crm.service.TransferGenCompanyService;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
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
@Api(tags = "推广管理-推广公司", description = "TransferGenCompanyController")
@RestController
@RequestMapping("/gen/company")
public class TransferGenCompanyController {

    private TransferGenCompanyService transferGenCompanyService;
    private TransferCompanySourceService transferCompanySourceService;
    private TransferSourceService transferSourceService;

    @Autowired
    public void setTransferCompanySourceService(TransferCompanySourceService transferCompanySourceService) {
        this.transferCompanySourceService = transferCompanySourceService;
    }

    @Autowired
    public void setTransferGenCompanyService(TransferGenCompanyService transferGenCompanyService) {
        this.transferGenCompanyService = transferGenCompanyService;
    }

    @Autowired
    public void setTransferSourceService(TransferSourceService transferSourceService) {
        this.transferSourceService = transferSourceService;
    }

    /**
     * 获取所有推广公司
     */
    @ApiOperation(value = "获取所有推广公司", notes = "获取所有推广公司，包含所有推广公司数据")
    @GetMapping("/get/all")
    public R getAllCompany() {
        return R.ok(transferGenCompanyService.getAllGenCompany());
    }

    /**
     * 推广公司目录管理树数据
     */
    @ApiOperation(value = "推广公司管理树数据", notes = "推广公司管理树数据，包含所有推广公司数据")
    @GetMapping("/tree")
    public R tree() {
        return R.ok(transferGenCompanyService.getRecursionTree(true));
    }

    /**
     * 分页查询推广公司
     */
    @ApiOperation(value = "分页查询-推广公司", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量], [sidx:排序字段],[treeKey:查询字段],[treeValue:查询字段值]\n" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【推广公司Id:companyId】【推广公司名称:name】,【根节点:parentId】【排序号:seq】,【备注:memo】,【状态(0 : 正常 1 : 禁用):status】\n" +
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
            "        \"name\": \"广州百单网网络科技有限公司\",\n" +
            "        \"parentId\": 1,\n" +
            "        \"seq\": 1,\n" +
            "        \"status\": 0,\n" +
            "        \"memo\": \"广州百单网网络科技有限公司\",\n" +
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
        PageInfo<TransferGenCompanyEntity> genCompanyPageInfo = new PageInfo<>(transferGenCompanyService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(genCompanyPageInfo);
    }

    /**
     * 新增推广公司
     */
    @ApiOperation(value = "新增推广公司", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【推广公司名称:name】,【排序号:seq】,【状态(0:正常; 1:禁用):status】, 【备注:memo】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"广州百单网网络科技有限公司\",\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "  \"memo\": \"广州百单网网络科技有限公司\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增推广公司")
    @PostMapping
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferGenCompanyEntity transferGenCompanyEntity) {
        int count = transferGenCompanyService.save(transferGenCompanyEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除推广公司
     */
    @ApiOperation(value = "删除推广公司", notes = "删除推广公司：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "companyId", value = "推广公司ID", required = true, dataType = "Long")
    @SysLog("删除推广公司")
    @DeleteMapping("/{companyId}")
    public R delete(@PathVariable("companyId") Long companyId) {
        int count = transferGenCompanyService.delete(companyId);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改推广公司
     */
    @ApiOperation(value = "修改推广公司", notes = "输入参数：\n" +
            "参数说明：\n" +
            " 【推广公司名称:name】,【排序号:seq】,【状态(0:正常; 1:禁用):status】, 【备注:memo】\n" +
            "示例：\n" +
            "{\n" +
            "  \"name\": \"广州百单网网络科技有限公司\",\n" +
            "  \"seq\": 1,\n" +
            "  \"status\": 1,\n" +
            "  \"memo\": \"广州百单网网络科技有限公司\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改推广公司")
    @PutMapping
    public R update(@Validated(RestfulValid.PUT.class) @RequestBody TransferGenCompanyEntity transferGenCompanyEntity) {
        int count = transferGenCompanyService.update(transferGenCompanyEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

    /**
     * 查询一个推广公司
     */
    @ApiOperation(value = "推广公司信息", notes = "推广公司信息: /info/1")
    @ApiImplicitParam(paramType = "path", name = "companyId", value = "推广公司ID", required = true, dataType = "Long")
    @SysLog("查询一个推广公司")
    @GetMapping("/{companyId}")
    public R info(@PathVariable("companyId") Long companyId) {
        TransferGenCompanyEntity transferGenCompanyEntity = transferGenCompanyService.findOne(companyId);
        if (transferGenCompanyEntity != null) {
            return R.ok(transferGenCompanyEntity);
        }
        return R.error(StatusCode.DATABASE_SELECT_FAILURE);
    }


    /**
     * 分页查询推广公司下的推广平台
     */
    @ApiOperation(value = "分页查询-推广平台", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】,【开始编号:startRow】,【结束编号:endRow】 \n" +
            "【平台名称:name】,【平台ID:sourceId】,【部门:deptId】,【部门名称:deptName】,【公司ID:companyId】【状态(0 : 正常 1 : 禁用):status】\n" +
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
            "        \"sourceId\": 2,\n" +
            "        \"name\": \"智联\",\n" +
            "        \"deptId\": 2,\n" +
            "        \"deptName\": \"益阳小区\",\n" +
            "        \"companyId\": 2,\n" +
            "        \"status\": 0,\n" +
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
    @RequestMapping(value = "/source/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    public R sourceList(@RequestParam HashMap<String, Object> pageParam,
                        @RequestBody(required = false) HashMap<String, Object> queryParam) {
        //查询列表数据
        PageInfo<TransferCompanySourceEntity> companySourceInfo = new PageInfo<>(transferCompanySourceService.findPageSource(PageQuery.build(pageParam, queryParam)));
        return R.ok(companySourceInfo);
    }

    /**
     * 获取不属于指定公司的推广平台
     */
    @GetMapping("/source/get/{companyId}")
    @ApiOperation(value = "获取不属于指定公司的推广平台", notes = "请求参数说明")
    public R listPage(@PathVariable("companyId") Long companyId) {
        return R.ok(transferSourceService.findNotByCompanyId(companyId));
    }

    /**
     * 获取属于指定公司的推广平台
     */
    @GetMapping("/source/get/all/{companyId}")
    @ApiOperation(value = "获取属于指定公司的推广平台", notes = "请求参数说明")
    public R getSourceByCompanyId(@PathVariable("companyId") Long companyId) {
        return R.ok(transferSourceService.findByCompanyId(companyId));
    }

    /**
     * 新增推广公司下的推广平台
     */
    @ApiOperation(value = "新增推广公司下的推广平台", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【推广公司:companyId】,【平台来源:sourceId】,【部门:deptId】,【部门名称:deptName】,【状态(0:正常; 1:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"companyId\": 2,\n" +
            "  \"sourceId\": 3,\n" +
            "  \"deptId\": 3,\n" +
            "  \"deptName\": \"益阳小区\",\n" +
            "  \"status\": 1,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增推广公司下的推广平台")
    @PostMapping("/source")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferCompanySourceDTO transferCompanySourceDTO) {
        int count = transferCompanySourceService.saveCompanySource(transferCompanySourceDTO);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_SAVE_FAILURE);
    }

    /**
     * 删除推广公司下的推广平台
     */
    @ApiOperation(value = "删除推广公司下的推广平台", notes = "删除推广平台：/delete/1")
    @ApiImplicitParam(paramType = "path", name = "sourceId", value = "推广平台ID", required = true, dataType = "Integer")
    @SysLog("删除推广公司")
    @DeleteMapping("/source/{id}")
    public R deleteSource(@PathVariable("id") Long id) {
        int count = transferCompanySourceService.delete(id);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_DELETE_FAILURE);
    }

    /**
     * 修改推广公司下的推广平台
     */
    @ApiOperation(value = "修改推广平台", notes = "输入参数：\n" +
            "参数说明：\n" +
            "【推广公司ID:companyId】,【平台来源:sourceId】,【部门:deptId】,【部门名称:deptName】,【状态(0:正常; 1:禁用):status】\n" +
            "示例：\n" +
            "{\n" +
            "  \"companyId\": 1,\n" +
            "  \"sourceId\": 2,\n" +
            "  \"deptId\": 2,\n" +
            "  \"deptName\": \"益阳小区\",\n" +
            "  \"status\": 1,\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("修改推广公司下的推广平台")
    @PutMapping("/source")
    public R updateSource(@Validated(RestfulValid.PUT.class) @RequestBody TransferCompanySourceEntity transferCompanySourceEntity) {
        int count = transferCompanySourceService.update(transferCompanySourceEntity);
        if (count > 0) {
            return R.ok();
        }
        return R.error(StatusCode.DATABASE_UPDATE_FAILURE);
    }

}
