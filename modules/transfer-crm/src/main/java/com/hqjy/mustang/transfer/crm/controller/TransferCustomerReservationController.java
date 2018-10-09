package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author gmm
 * @ description
 * @ date create in 2018年9月20日14:21:03
 */
@Api(tags = "客户管理-预约", description = "TransferCustomerReservationController")
@RestController
@RequestMapping("/customer/reservation")
public class TransferCustomerReservationController extends AbstractMethodError {

    @Autowired
    private TransferCustomerReservationService transferCustomerReservationService;
    @Autowired
    private SysDeptServiceFeign sysDeptServiceFeign;

    @ApiOperation(value = "分页查询-预约客户列表", notes = "请求参数：\n" +
            "分页参数(requestParam数据格式接收)：[pageNum:当前页],[pageSize:每页的数量]\n" +
            "高级查询参数（RequestBody数据格式接收）：[姓名:name],[手机:phone],[上门状态(0-未上门，1-已上门)：visitStatus]\n" +
            "[beginCreateTime:开始创建时间]，[endCreateTime:结束创建时间]，[beginAppointmentTime:开始预约时间]，[endAppointmentTime:结束预约时间]\n" +
            "返回参数：【id:预约主键id】，【当前页:currPage】，【当前页的数量:size】【总记录数:totalCount】,【总页数:totalPage】,【每页的数量:pageSize】\n" +
            "【appointmentTime:预约时间】,【visitStatus:到访状态，（见数字字典VISIT_STATUS）:默认0-未上门，1-已上门】,【createUserId：创建人id】,【createUserName：创建人名称】\n" +
            "【createTime：创建时间】,【phone：手机】,【teacherCode:预约老师编号】,【teacherName:预约老师名称】,【name：客户姓名】,【customerId:客户编号】\n" +
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
            "        \"customerId\": 23,\n" +
            "        \"teacherCode\": \"45824\",\n" +
            "        \"teacherName\": \"胡小芳\",\n" +
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
//    @RequiresPermissions("biz:appointment:list")
    public R list(@RequestParam HashMap<String, Object> pageParam,
                  @RequestBody(required = false) HashMap<String, Object> queryParam) {
        PageInfo<TransferCustomerReservationEntity> deptPageInfo = new PageInfo<>(transferCustomerReservationService.findPage(PageQuery.build(pageParam, queryParam)));
        return R.ok(deptPageInfo);
    }

    @ApiOperation(value = "预约详情", notes = "请求参数（RequestParam接收）：[id:预约主键id]\n" +
            "示例： url?id=2\n" +
            "响应数据格式\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"result\": {\n" +
            "    \"appointmentTime\": \"2018-06-15 00:00:00\",\n" +
            "    \"createUserId\": 1,\n" +
            "    \"createTime\": \"2018-06-15 11:32:18\",\n" +
            "    \"customerId\": 23,\n" +
            "    \"id\": 1,\n" +
            "    \"deptId\": 1\n" +
            "    \"deptName\": \"广州东圃校区\",\n" +
            "    \"teacherCode\": \"00878\",\n" +
            "    \"teacherName\": \"犹静\",\n" +
            "    \"visitStatus\": 1\n" +
            "  },\n" +
            "  \"code\": 0\n" +
            "}")
    @PostMapping("/appointmentInfo")
//    @RequiresPermissions("biz:appointment:list")
    public R appointmentInfo(@RequestParam("id") Long id) {
        return transferCustomerReservationService.findOne(id) != null ? R.ok(transferCustomerReservationService.findOne(id)) : R.error("不存在数据");
    }

    @ApiOperation(value = "预约详情更新保存", notes = "请求参数（RequestBody)：" +
            "示例：\n" +
            "{\n" +
            "\"id\": 1,\n" +
            "\"visitStatus\": -1\n" +
            "}\n" +
            "响应数据格式：\n" +
            "{\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"code\": 1\n" +
            "}")
    @PostMapping("/updateAppointment")
//    @RequiresPermissions("biz:appointment:update")
    @SysLog("预约详情更新保存")
    public R updateAppointment(@RequestBody TransferCustomerReservationEntity entity) {
        return transferCustomerReservationService.update(entity) > 0 ? R.ok() : R.error("更新失败");
    }

    @ApiOperation(value = "客户预约/再次预约操作", notes = "请求参数：[customerId:客户id],[appointmentTime:预约时间],[deptId:部门编号]\n" +
            "[deptName:部门名称],[status（0-未转NC，1-已转NC）:转NC状态],[visitStatus(0-未上门，1-以上门):到访状态]\n" +
            "[teacherCode:老师编号],[teacherName:老师名字]\n" +
            "示例：\n" +
            "{\n" +
            "  \"appointmentTime\": \"2018-06-15\",\n" +
            "  \"status\": 0,\n" +
            "  \"visitStatus\": 0,\n" +
            "  \"customerId\": 23,\n" +
            "  \"deptName\": \"武汉光谷校区\",\n" +
            "  \"deptId\": 1,\n" +
            "  \"teacherCode\": \"00878\",\n" +
            "  \"teacherName\": \"犹静\",\n" +
            "}")
    @PostMapping("/reserveCustomer")
//    @RequiresPermissions("biz:appointment:save")
    @SysLog("客户预约/再次预约操作")
    public R reserveCustomer(@RequestBody TransferCustomerReservationEntity reservationEntity) {
        return transferCustomerReservationService.reserveCustomer(reservationEntity);
    }
}
