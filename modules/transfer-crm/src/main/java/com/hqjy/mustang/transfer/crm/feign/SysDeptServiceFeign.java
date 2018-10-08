package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.SysDeptEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author xyq
 * @date 2018年10月8日16:29:46
 */
@FeignClient(name = "mustang-admin")
public interface SysDeptServiceFeign {

    @GetMapping(Constant.API_PATH + "/dept/all")
    R getAllDept();

    @GetMapping(Constant.API_PATH + "/dept/all/id")
    List<Long> getAllDeptId(Long deptId);

    @GetMapping(Constant.API_PATH + "/dept/customerId/userId")
    List<Long> getUserDeptIdList(Long userId);

    /**
     * 查询用户对应的部门
     *
     * @param userId 用户ID
     * @return 返回该用户部门信息
     * @author xyq
     * @date xyq 2018年10月8日16:22:32
     */
    @GetMapping(value = "/getUserDeptList")
    List<SysDeptEntity> getUserDeptList(@RequestParam("userId") Long userId);
}
