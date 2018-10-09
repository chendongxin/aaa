package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @author xyq 2018年10月9日17:02:22
 */
@FeignClient(name = "mustang-admin")
public interface SysUserDeptServiceFeign {


    /**
     * 获取用户所有的部门ID
     *
     * @param userId 用户id
     * @return 返回用户所有部门id集合
     * @author by xyq 2018年10月9日17:01:10
     */
    @GetMapping(Constant.API_PATH + "/user/dept/all/{userId}")
    List<Long> getUserDeptIdList(@PathVariable("userId") Long userId);

}
