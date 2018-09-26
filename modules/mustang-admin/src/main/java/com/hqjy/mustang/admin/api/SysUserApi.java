package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.dao.SysUserDao;
import com.hqjy.mustang.admin.service.SysUserService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
