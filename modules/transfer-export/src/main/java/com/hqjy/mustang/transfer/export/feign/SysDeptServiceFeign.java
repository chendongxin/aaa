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
     * 根据部门Id取该部门旗下部门信息（包括所选部门）
     *
     * @param deptId 部门id
     * @return 返回部门集合信息
     * @author xyq 2018年10月12日09:55:50
     */
    @GetMapping(value = Constant.API_PATH + "/dept/getDeptEntityByDeptId")
    List<SysDeptInfo> getDeptEntityByDeptId(@RequestParam("deptId") Long deptId);

}
