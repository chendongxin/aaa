package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "mustang-admin")
public interface SysDeptServiceFeign {

    @GetMapping(Constant.API_PATH + "/dept/all")
    R getAllDept();

    @GetMapping(Constant.API_PATH + "/dept/all/id")
    List<Long> getAllDeptId(Long deptId);

    @GetMapping(Constant.API_PATH + "/dept/customerId/userId")
    List<Long> getUserDeptIdList(Long userId);

    @GetMapping(Constant.API_PATH + "/dept/userId")
    R getUserDeptList(Long userId);
}
