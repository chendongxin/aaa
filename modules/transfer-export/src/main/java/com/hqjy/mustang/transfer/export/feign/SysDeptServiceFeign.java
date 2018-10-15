package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author xyq
 * @date 2018年10月12日09:22:39
 */
@FeignClient(name = "mustang-admin")
public interface SysDeptServiceFeign {


    /**
     * 根据部门名称获取部门Id
     *
     * @param deptName 部门名称
     * @return 返回该用户部门ID
     * @author xyq
     * @date xyq 2018年10月12日09:21:22
     */
    @GetMapping(value = Constant.API_PATH + "/dept/getUserDeptList")
    Long getDeptByName(@RequestParam("deptName") String deptName);


    /**
     * 获取所选部门的旗下部门（包括所选部门）
     *
     * @param deptId 部门ID
     * @return 返回部门集合
     * @author ADD xyq 2018年10月12日09:29:30
     */
    @GetMapping(value = Constant.API_PATH + "/dept/getAllDeptUnderDeptId")
    List<Long> getAllDeptUnderDeptId(@RequestParam("deptId") Long deptId);


    /**
     * 根据部门名称取该部门旗下部门信息（包括所选部门）
     *
     * @param deptName 部门名称
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:55:50
     */
    @GetMapping(value = Constant.API_PATH + "/dept/getDeptEntityByDeptName")
    List<SysDeptInfo> getDeptEntityByDeptName(@RequestParam("deptName") String deptName);
}
