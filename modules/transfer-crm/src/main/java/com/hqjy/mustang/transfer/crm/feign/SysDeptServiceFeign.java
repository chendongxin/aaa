package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author xyq
 * @date 2018年10月8日16:29:46
 */
@FeignClient(name = "mustang-admin")
public interface SysDeptServiceFeign {

    @GetMapping(Constant.API_PATH + "/dept/all/{deptId}")
    List<Long> getAllDeptId(@PathVariable("deptId") Long deptId);

    /**
     * 查询用户对应的部门
     *
     * @param userId 用户ID
     * @return 返回该用户部门信息
     * @author xyq
     * @date xyq 2018年10月8日16:22:32
     */
    @GetMapping(Constant.API_PATH + "/dept/getUserDeptList/{userId}")
    List<SysDeptInfo> getUserDeptList(@PathVariable("userId") Long userId);

    /**
     * 获取用户所属部门
     */
    @GetMapping(Constant.API_PATH + "/dept/userDept/{userId}")
    SysDeptInfo getUserDept(@PathVariable("userId") Long userId);
}
