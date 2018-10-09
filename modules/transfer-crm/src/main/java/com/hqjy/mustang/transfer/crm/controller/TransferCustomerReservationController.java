package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author xyq
 * @date create in 2018年10月9日09:01:14
 */
@Api(tags = "客户管理-预约客户", description = "TransferCustomerReservationController")
@RestController
@RequestMapping("/transfer/reservation")
public class TransferCustomerReservationController {

    private TransferCustomerReservationService transferCustomerReservationService;

    @Autowired
    public void setTransferCustomerReservationService(TransferCustomerReservationService transferCustomerReservationService) {
        this.transferCustomerReservationService = transferCustomerReservationService;
    }

    @ApiOperation(value = "分页查询-预约客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[客户姓名:name],[手机:phone],[上门状态(0-未上门，1-已上门)：visitStatus]\n" +
            "[beginCreateTime:开始创建时间]，[endCreateTime:结束创建时间]，[beginAppointmentTime:开始预约时间]，[endAppointmentTime:结束预约时间]\n" +
            "返回参数：【id:预约主键id】，【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【appointmentTime:预约时间】,【visitStatus:上门状态，（见数字字典VISIT_STATUS）:默认0-未上门，1-已上门】,【createUserId：归属人id】,【createUserName：归属人名称】\n" +
            "【createTime：创建时间】,【phone：客户电话】,【name：客户姓名】,【customerId:客户编号】\n" +
            "响应数据示例：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"currPage\": 1,\n" +
            "    \"endRow\": 1,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"id\": \"1\",\n" +
            "        \"appointmentTime\": \"2018-06-15 00:00:00\",\n" +
            "        \"visitStatus\": \"0\",\n" +
            "        \"createUserId\": \"10\",\n" +
            "        \"createUserName\": \"admin\",\n" +
            "        \"createTime\": \"2018-06-15 11:26:02\",\n" +
            "        \"name\": \"梅西\",\n" +
            "        \"phone\": \"13631156666\",\n" +
            "        \"customerId\": 23\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pageSize\": 10,\n" +
            "    \"size\": 1,\n" +
            "    \"startRow\": 1,\n" +
            "    \"totalCount\": 1,\n" +
            "    \"totalPage\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @RequestMapping(value = "/listPage", method = {RequestMethod.POST, RequestMethod.GET})
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam) {
        return R.ok(new PageInfo<>(transferCustomerReservationService.findPage(PageQuery.build(pageParam, queryParam))));
    }

    @ApiOperation(value = "预约详情更新", notes = "请求参数（RequestBody)：" +
            "示例：\n" +
            "{\n" +
            "\"id\": 1,\n" +
            "\"visitStatus\": 1,\n" +
            "\"intention\": 1,\n" +
            "\"validVisit\": 1\n" +
            "}\n" +
            "响应数据格式：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 1\n" +
            "}")
    @PostMapping("/updateAppointment")
    @SysLog("预约详情更新")
    public R updateAppointment(@RequestBody TransferCustomerReservationEntity entity) {
        return transferCustomerReservationService.update(entity) > 0 ? R.ok() : R.error("更新失败");
    }

    @ApiOperation(value = "预约详情", notes = "请求参数（RequestParam接收）：[id:预约主键id]\n" +
            "返回参数说明：【appointmentTime：预约时间】，【visitStatus：上门状态,见典VISIT_STATUS(0-未上门，1-已上门)】\n" +
            "【intention：意向度（0-否，1-是）】，【validVisit：有效上门（0-否，1-是）】【id：预约单主键】，[teacherName:校区老师]\n" +
            "示例： url?id=2\n" +
            "响应数据格式\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"customerId\": 23,\n" +
            "    \"id\": 2,\n" +
            "    \"appointmentTime\": \"2018-06-15 11:32:18\",\n" +
            "    \"teacherName\": \"盛少帅\"\n" +
            "    \"intention\": 1,\n" +
            "    \"validVisit\": 1,\n" +
            "    \"visitStatus\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}")
    @PostMapping("/appointmentInfo")
    public R appointmentInfo(@RequestParam("id") Long id) {
        return transferCustomerReservationService.findOne(id) != null ? R.ok(transferCustomerReservationService.findOne(id)) : R.error("不存在数据");
    }

    @ApiOperation(value = "客户预约", notes = "请求参数：[customerId:客户id],[appointmentTime:预约时间],[deptId:部门编号],[deptName:部门名称]\n" +
            "[teacherCode:校区老师编号],[teacherName:校区老师名称]" +
            "示例：\n" +
            "{\n" +
            "  \"appointmentTime\": \"2018-06-15\",\n" +
            "  \"customerId\": 23,\n" +
            "  \"deptName\": \"武汉光谷校区\",\n" +
            "  \"deptId\": 1,\n" +
            "  \"teacherCode\": \"01096\",\n" +
            "  \"teacherName\": \"盛少帅\"\n" +
            "}")
    @PostMapping("/reserveCustomer")
    @SysLog("客户预约")
    public R reserveCustomer(@RequestBody TransferCustomerReservationEntity reservationEntity) {
        return transferCustomerReservationService.reserveCustomer(reservationEntity);
    }

    @ApiOperation(value = "转商机", notes = "请求参数：[customerId:客户id],[id:预约id]\n" +
            "请求参数示例：\n" +
            "{\n" +
            "    \"customerId\": 243,\n" +
            "    \"id\": 50\n" +
            "}\n"
    )
    @PostMapping("/transferToNc")
    @SysLog("预约转NC商机")
    public R transferToNc(@RequestBody TransferCustomerReservationEntity appointmentEntity) {
        return transferCustomerReservationService.transferToNc(appointmentEntity);

    }

    @ApiOperation(value = "获取上门记录", notes = "请求参数：[customerId:客户id]\n" +
            "请求参数示例：http://localhost:8300/transfer/reservation/getReservationByCustomerId?customerId=215\n" +
            "参数说明：【visitTime：上门时间】,【intention：意向度见数据字典INTENTION(0-否，1-是)】" +
            "请求结果：无上门记录：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": [],\n" +
            "  \"code\": 0\n" +
            "}\n" +
            "请求结果：有上门记录：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"intention\": 1,\n" +
            "      \"visitTime\": \"2018-10-09 17:51:12\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"code\": 0\n" +
            "}"
    )
    @GetMapping("/getReservationByCustomerId")
    public R getReservationByCustomerId(@RequestParam("customerId") Long customerId) {
        return R.result(transferCustomerReservationService.getReservationByCustomerId(customerId));

    }
}
