package com.hqjy.mustang.transfer.export.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
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

    /**
     * 根据部门Id获取所选部门下（含子部门，本部门）的电销专员及其对应电销部门
     *
     * @param deptId 部门Id
     * @return 返回
     * @author xyq 2018年11月1日10:02:26
     */
    @GetMapping(value = Constant.API_PATH + "/getUserDeptInfoByDeptId")
    List<UserDeptInfo> getUserDeptInfoByDeptId(@RequestParam("deptId") Long deptId);


    /**
     * 获取所有角色为客服的用户
     *
     * @return 返回 客服用户信息
     * @author xyq 2018年10月31日15:06:36
     */
    @GetMapping(value = Constant.API_PATH + "user/getCustomerUser")
    List<SysUserInfo> getCustomerUser();

    /**
     * 根据部门ID集合字符串和角色编号获取用户和部门信息
     *
     * @return 返回 角色为客服的用户信息以及其所负责的电销部门数据
     * @author xyq 2018年10月31日16:14:26
     */
    @GetMapping(value = Constant.API_PATH + "/getUserDeptByRoleCode")
    List<UserDeptInfo> getUserDeptByRoleCode();

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

            @Override
            public List<SysUserInfo> getCustomerUser() {
                LOGGER.error("调用{}异常:{},原因：{}", "SysUserDeptServiceFeign.getCustomerUser()", throwable.getMessage());
                return new ArrayList<>();
            }

            @Override
            public List<UserDeptInfo> getUserDeptByRoleCode() {
                LOGGER.error("调用{}异常:{},原因：{}", "SysUserDeptServiceFeign.getUserDeptByRoleCode()", throwable.getMessage());
                return new ArrayList<>();
            }

            @Override
            public List<UserDeptInfo> getUserDeptInfoByDeptId(Long deptId) {
                LOGGER.error("调用{}异常:{},原因：{}", "SysUserDeptServiceFeign.getUserDeptInfoByDeptId()",deptId, throwable.getMessage());
                return new ArrayList<>();
            }
        };
    }


}
