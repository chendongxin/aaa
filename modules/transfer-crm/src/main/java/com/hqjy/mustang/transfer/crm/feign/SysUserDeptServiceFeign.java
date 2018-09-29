package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "mustang-admin")
public interface SysUserDeptServiceFeign {

    @GetMapping(Constant.API_PATH + "/user/dept/all")
    List<Long> getUserDeptIdList(Long userId);

}
