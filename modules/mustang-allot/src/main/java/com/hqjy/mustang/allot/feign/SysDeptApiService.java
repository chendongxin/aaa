package com.hqjy.mustang.allot.feign;

import com.hqjy.mustang.allot.feign.impl.SysDeptApiServiceFallbackImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 部门
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-admin", fallback = SysDeptApiServiceFallbackImpl.class)
public interface SysDeptApiService {

    /**
     * 根据用户Id查询部门
     */
    @GetMapping(value = Constant.API_PATH + "/dept/{deptId}")
    SysDeptInfo findOne(@PathVariable("deptId") Long deptId);

}
