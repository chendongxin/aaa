package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerTransferDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerUpDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDetailService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @author gmm
 * @ description
 * @ date create in 2018年9月20日14:21:03
 */
@Api(tags = "客户管理", description = "TransferCustomerController")
@RestController
@RequestMapping("/customer")
public class TransferCustomerController extends AbstractMethodError {

    @Autowired
    private TransferCustomerService transferCustomerService;
    @Autowired
    private TransferCustomerDetailService transferCustomerDetailService;

    /**
     * 分页查询客户列表
     */
    @ApiOperation(value = "分页查询-客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）:【商机状态1-(失败)有效, 2-(失败)无效, 3-预约, 4-成交)):status】\n" +
            "【赛道名称:proName】,【部门名称:deptName】,【手机:phone】,【归属人:userName】\n" +
            "【beginCreateTime:开始创建时间】,【endCreateTime:结束创建时间】" +
            "返回参数：【当前页:currPage】,【当前页的数量:size】,【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【客户编号:customerId】,【NC客户编号:ncId】,【赛道编号:proId】,【赛道名称:proName】,【来源平台ID:sourceId】,【来源平台名称:sourceName】\n" +
            "【推广公司ID:companyId】,【推广公司名称:companyName】,【姓名:name】,【商机状态1-(失败)有效, 2-(失败)无效, 3-预约, 4-成交)):status】\n" +
            "【推广方式(1-主动获取，2-被动获取):getWay】,【当前跟进人ID:userId】,【当前跟进人姓名:userName】,【当前跟进人部门ID:deptId】\n" +
            "【当前跟进人部门名称:deptName】,【首次跟进人ID:firstUserId】,【首次跟进人姓名:firstUserName】,【首次跟进人所属部门:firstUserDeptId】,【最后跟进人ID:lastUserId】\n" +
            "【最后跟进人姓名:lastUserName】,【最后跟进人所属部门:lastUserDeptId】,【推广用户ID:genUserId】,【推广用户姓名:genUserName】,【手机号码:phone】,【微信:weChat】\n" +
            "【QQ:qq】,【座机:landLine】,【创建人ID:createUserId】,【创建人姓名:createUserName】,【创建时间:createTime】\n" +
            "【更新人编号:updateUserId】,【更新人姓名:updateUserName】,【更新时间:updateTime】\n" +
            "示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"customerId\": 1,\n" +
            "        \"ncId\": 1,\n" +
            "        \"proId\": 1,\n" +
            "        \"proName\": \"IT赛道\",\n" +
            "        \"sourceId\": 1,\n" +
            "        \"sourceName\": \"智联\",\n" +
            "        \"companyId\": 1,\n" +
            "        \"companyName\": \"广州海度网络科技有限公司\",\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"status\": 1,\n" +
            "        \"getWay\": 1,\n" +
            "        \"userId\": 1,\n" +
            "        \"userName\": \"王超\",\n" +
            "        \"deptId\": 1,\n" +
            "        \"deptName\": \"益阳校区\",\n" +
            "        \"firstUserId\": 1,\n" +
            "        \"firstUserName\": \"丫丫\",\n" +
            "        \"firstUserDeptId\": 1,\n" +
            "        \"lastUserId\": 1,\n" +
            "        \"lastUserName\": \"美丽\",\n" +
            "        \"lastUserDeptId\": 1,\n" +
            "        \"genUserId\": 1,\n" +
            "        \"genUserName\": \"翰林\",\n" +
            "        \"phone\": \"18578548523\",\n" +
            "        \"weChat\": \"18578548523\",\n" +
            "        \"qq\": \"18578548523\",\n" +
            "        \"landLine\": \"6541236\",\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"王超群\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
            "        \"updateUserId\": 1,\n" +
            "        \"updateUserName\": \"王超群\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
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
//    @RequiresPermissions("customer:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerEntity> deptPageInfo = new PageInfo<>(transferCustomerService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    /**
     * 获取某客户的基本资料
     * @param customerId
     * @return 客户的具体信息
     */
    @ApiOperation(value = "获取某客户的基本数据资料", notes = "请求参数:[customerId:客户编号]\n" +
            " 响应示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "        \"customerId\": 1,\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"status\": 1,\n" +
            "        \"deptId\": 1,\n" +
            "        \"deptName\": \"广州东圃校区\",\n" +
            "        \"createUserId\": 1,\n" +
            "        \"createUserName\": \"王超d群\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
            "        \"sex\": 1,\n" +
            "        \"age\": 18,\n" +
            "        \"getWay\": 1,\n" +
            "        \"educationId(0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士，7-博士)\": 1,\n" +
            "        \"positionApplied\": \"工程师\",\n" +
            "        \"major\": \"软件\",\n" +
            "        \"school\": \"中大\",\n" +
            "        \"applyType(1-开发类，2-设计类，3-运营类，4-产品类，5-技术支持类，6-其它)\": 1,\n" +
            "        \"graduateDate\": \"2018-06-11\",\n" +
            "        \"applyKey\": \"软件\",\n" +
            "        \"workingPlace\": \"广州\",\n" +
            "        \"workExperience\": \"2\",\n" +
            "        \"resumeDetail\": \"广州-工程师\",\n" +
            "        \"note\": \"无\",\n" +
            "    \"contactList\": [\n" +
            "      {\n" +
            "        \"contactId\": 1,\n" +
            "        \"createId\": 1,\n" +
            "        \"createTime\": \"2018-06-04 16:11:46\",\n" +
            "        \"customerId\": 1,\n" +
            "        \"detail\": \"13631153950\",\n" +
            "        \"isPrimary\": 1,\n" +
            "        \"type\": 0,\n" +
            "        \"updateTime\": \"2018-06-04 16:27:34\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"contactId\": 2,\n" +
            "        \"createId\": 1,\n" +
            "        \"createTime\": \"2018-06-04 16:27:32\",\n" +
            "        \"customerId\": 1,\n" +
            "        \"detail\": \"微信号\",\n" +
            "        \"isPrimary\": 0,\n" +
            "        \"type\": 1,\n" +
            "        \"updateTime\": \"2018-06-04 16:27:55\"\n" +
            "      }\n" +
            "    ],\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @GetMapping("/getCustomerData")
    public R getCustomerData(@RequestParam("customerId") Long customerId) {
        return R.ok(transferCustomerService.getCustomerData(customerId));
    }

    /**
     * 新增客户
     */
    @ApiOperation(value = "新增客户", notes = "请求参数：\n" +
            "参数说明：\n" +
            "【手机:phone】,【座机：landLine】,【微信:weiXin】,【QQ:qq】,【赛道ID:proId】,【赛道名称:proName】,【来源平台ID:sourceId】,【来源平台名称:sourceName】,【部门ID:deptId】,【部门名称:deptName】\n" +
            "【推广公司ID:companyId】,【推广公司名称:companyName】,【首次跟进人ID:firstUserId】,【首次跟进人名称:firstUserName】,【姓名:name】,【性别(0-未知，1-男，2-女):sex】\n" +
            "【年龄:age】,【学历(0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士研究生，7-博士研究生):educationId】\n" +
            "【学校:school】,【专业:major】,【期望职位:positionApplied】,【毕业年份:graduateDate】,【应聘类别:applyType】\n" +
            "【期望工作地点:workingPlace】,【应聘关键词:applyKey】,【获取方式(1-主动获取，2-被动获取):getWay】,【工作经验(0-无经验，1-应届生，2-一年以内，3-两年，4-三年，5-三年以上):workExperience】,【备注:note】\n" +
            "示例：\n" +
            "{\n" +
            "  \"phone\": \"18578548523\",\n" +
            "  \"weChat\": \"18578548523\",\n" +
            "  \"qq\": \"18578548523\",\n" +
            "  \"landLine\": \"6541236\",\n" +
            "  \"proId\": 106,\n" +
            "  \"proName\": \"未分配部门\",\n" +
            "  \"sourceId\": 3,\n" +
            "  \"sourceName\": \"前程\",\n" +
            "  \"deptId\": 1709,\n" +
            "  \"deptName\": \"飞虎自考网销\",\n" +
            "  \"companyId\": 2,\n" +
            "  \"companyName\": \"广州百单网网络科技有限公司\",\n" +
            "  \"firstUserId\": 10143,\n" +
            "  \"firstUserName\": \"李倩\",\n" +
            "  \"name\": \"丫丫\",\n" +
            "  \"sex\": 1,\n" +
            "  \"age\": 24,\n" +
            "  \"educationId\": 5,\n" +
            "  \"school\": \"中山大学\",\n" +
            "  \"major\": \"软件\",\n" +
            "  \"positionApplied\": \"测试\",\n" +
            "  \"graduateDate\": \"2018-06-11 17:19:24\",\n" +
            "  \"applyType\": 26,\n" +
            "  \"applyKey\": 27,\n" +
            "  \"workingPlace\": \"广州\",\n" +
            "  \"getWay\": 1,\n" +
            "  \"workExperience\": 5,\n" +
            "  \"note\": \"无\",\n" +
            "}\n" +
            "新增成功响应数据：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 0\n" +
            "}")
    @SysLog("新增客户")
    @PostMapping("/save")
//    @RequiresPermissions("biz:customer:save")
    public R save(@Validated(RestfulValid.POST.class) @RequestBody TransferCustomerDTO customerDto) {
        try {
            return transferCustomerService.saveTransferCustomer(customerDto);
        } catch (Exception e) {
            return R.error("客户新增异常：" + e.getMessage());
        }
    }

    /**
     * 转商机
     * @param dto
     * @return 转移结果
     */
    @ApiOperation(value = "客户转移", notes = "请求参数说明：【customerId:客户编码数组类型】,【deptId:部门id】,【userId:用户人员】\n" +
            "部门、人员都为必选；确认后，商机转移到指定人员\n" +
            "示例：\n" +
            "{\n" +
            "  \"customerId\": [1,8],\n" +
            "  \"deptId\": 3,\n" +
            "  \"userId\": 10\n" +
            "}")
    @PostMapping("/transferCustomer")
//    @RequiresPermissions("biz:customer:transfer")
    @SysLog("客户转移")
    public R transferCustomer(@RequestBody TransferCustomerTransferDTO dto) {
        return transferCustomerService.transferCustomer(dto);
    }

    /**
     * 保存修改客户基本资料
     * @param customerDetail
     * @return 修改结果
     */
    @ApiOperation(value = "保存修改客户基本资料", notes = "输入参数：\n" +
            "参数说明：" +
            "【客户ID:customerId】,【姓名:name】,【性别(0-未知，1-男，2-女):sex】,【创建时间:createTime】,【年龄:age】\n" +
            "【获取方式(1-主动获取; 2-被动获取):getWay】,【学历(0-无，1-小学，2-初中，3-高中，4-大专，5-本科，6-硕士，7-博士):educationId】\n" +
            "【应聘职位:positionApplied】,【专业:major】,【学校:school】,【应聘类别(1-开发类，2-设计类，3-运营类，4-产品类，5-技术支持类，6-其它):applyType】\n" +
            "【毕业时间:graduateDate】,【应聘关键词:applyKey】,【期望工作地点:workingPlace】,【工作经验:workExperience】,【备注:note】\n" +
            "示例：\n" +
            "{\n" +
            "        \"customerId\": 1,\n" +
            "        \"name\": \"灵儿\",\n" +
            "        \"createTime\": \"2018-06-11 17:19:24\",\n" +
            "        \"sex\": 1,\n" +
            "        \"age\": 18,\n" +
            "        \"getWay\": 1,\n" +
            "        \"educationId\": 1,\n" +
            "        \"positionApplied\": \"工程师\",\n" +
            "        \"major\": \"软件\",\n" +
            "        \"school\": \"中大\",\n" +
            "        \"applyType\": 1,\n" +
            "        \"graduateDate\": \"2018-06-11\",\n" +
            "        \"applyKey\": \"软件\",\n" +
            "        \"workingPlace\": \"广州\",\n" +
            "        \"workExperience\": \"2\",\n" +
            "        \"note\": \"无\",\n" +
            "}")
    @SysLog("保存修改客户资料")
    @PostMapping("/update")
    public R update(@RequestBody TransferCustomerDetailEntity customerDetail) {
        try {
            transferCustomerDetailService.updateCustomerDetail(customerDetail);
            return R.ok();
        } catch (Exception e) {
            return R.error("客户资料修改异常：" + e.getMessage());
        }
    }

    @ApiOperation(value = "导入客户列表", notes = "请求参数方式：multipart/form-data,支持多参数类型请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proId", paramType = "query", value = "赛道ID", dataType = "Long"),
            @ApiImplicitParam(name = "companyId", paramType = "query", value = "推广公司ID", dataType = "Long"),
            @ApiImplicitParam(name = "deptId", paramType = "query", value = "部门ID", dataType = "Long"),
            @ApiImplicitParam(name = "firstUserId", paramType = "query", value = "首次跟进人ID", dataType = "Long"),
            @ApiImplicitParam(name = "sourceId", paramType = "query", value = "来源平台ID", dataType = "Long"),
            @ApiImplicitParam(name = "getWay", paramType = "query", value = "获取方式", dataType = "Byte"),
            @ApiImplicitParam(name = "notAllot", paramType = "query", value = "是否不分配：true-不分配，false-自动分配", dataType = "boolean")
    })
    @PostMapping("/importCustomer")
//    @RequiresPermissions("biz:customer:import")
    @SysLog("客户列表导入")
    public R importCustomer(@RequestParam("file") MultipartFile file, @ModelAttribute TransferCustomerUpDTO dto) {
        return transferCustomerService.importCustomer(file, dto);
    }

}
