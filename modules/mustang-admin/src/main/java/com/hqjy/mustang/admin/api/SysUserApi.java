package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.dao.SysUserDao;
import com.hqjy.mustang.admin.model.entity.SysUserEntity;
import com.hqjy.mustang.admin.service.SysUserService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import com.hqjy.mustang.common.model.admin.UserDeptInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:31
 */
@RestController
@RequestMapping(Constant.API_PATH + "/user")
public class SysUserApi {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 根据用户Id查询
     */
    @GetMapping("/{userId}")
    public SysUserInfo findByUserId(@PathVariable("userId") Long userId) {
        return PojoConvertUtil.convert(sysUserDao.findOne(userId), SysUserInfo.class);
    }

    /**
     * 根据用户Id查询详细信息
     */
    @GetMapping("/findOneInfo/{userId}")
    public SysUserInfo findOneInfo(@PathVariable("userId") Long userId) {
        return PojoConvertUtil.convert(sysUserService.findOneInfo(userId), SysUserInfo.class);
    }


    /**
     * 获取所有角色为客服的用户
     *
     * @return 返回 客服用户信息
     * @author xyq 2018年10月31日15:06:36
     */
    @ApiOperation(value = "获取所有角色为客服的用户")
    @GetMapping(value = "/getCustomerUser")
    public List<SysUserEntity> getCustomerUser() {
        return sysUserService.getUserListByRoleCode(Constant.Role.SERVICE_COMMISSIONER.getCode());
    }
}
