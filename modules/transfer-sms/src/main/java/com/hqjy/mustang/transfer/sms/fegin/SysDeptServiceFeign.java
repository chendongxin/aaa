package com.hqjy.mustang.transfer.sms.fegin;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description:
 * Autor: hutao
 * Date: 2018-10-24-14:57
 */
@FeignClient(name = "mustang-admin")
public interface SysDeptServiceFeign {

    /**
     * description: 获取所选部门下的所有部门
     * @author: hutao
     * @date 2018/10/24 15:00
     */
    @GetMapping(Constant.API_PATH + "/dept/all/{deptId}")
    List<Long> getAllDeptId(@PathVariable("deptId") Long deptId);

    /**
     * description: 获取用户所有的部门ID
     * @author: hutao
     * @date 2018/10/24 15:24
     */
    @GetMapping(Constant.API_PATH + "/getUserDeptIdList")
    List<Long> getUserDeptIdList(@RequestParam("userId") Long userId);
}
