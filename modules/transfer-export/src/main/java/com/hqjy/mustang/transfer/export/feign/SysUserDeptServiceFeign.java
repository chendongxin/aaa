package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xyq 2018年10月9日17:02:22
 */
@FeignClient(name = "mustang-admin", fallbackFactory = UserDeptServiceFallbackFactory.class)
public interface SysUserDeptServiceFeign {


    /**
     * 获取用户所有的部门ID
     *
     * @param userId 用户id
     * @return 返回用户所有部门id集合
     * @author by xyq 2018年10月9日17:01:10
     */
    @GetMapping(value = Constant.API_PATH + "/getUserDeptIdList")
    List<Long> getUserDeptIdList(@RequestParam("userId") Long userId);


    /**
     * 根据部门名称集合字符串获取用户和部门信息
     *
     * @param deptName 部门名称
     * @return 返回
     */
    @GetMapping(value = Constant.API_PATH + "/getUserDeptInfo")
    List<UserDeptInfo> getUserDeptInfo(@RequestParam("deptName") String deptName);

}

@Component
class UserDeptServiceFallbackFactory implements FallbackFactory<SysUserDeptServiceFeign> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDeptServiceFallbackFactory.class);

    @Override
    public SysUserDeptServiceFeign create(Throwable throwable) {
        return new SysUserDeptServiceFeign() {
            @Override
            public List<Long> getUserDeptIdList(Long userId) {
                LOGGER.error("调用方法{}异常:{},原因：{}", "SysUserDeptServiceFeign.getUserDeptIdList(Long userId)", userId, throwable.getMessage());
                return new ArrayList<>();
            }

            @Override
            public List<UserDeptInfo> getUserDeptInfo(String deptName) {
                LOGGER.error("调用{}异常:{},原因：{}", "SysUserDeptServiceFeign.getUserDeptInfo(String deptName)", deptName, throwable.getMessage());
                return new ArrayList<>();
            }
        };
    }


}
