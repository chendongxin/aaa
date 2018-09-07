package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户扩展信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:31
 */
@RestController
@RequestMapping(Constant.API_PATH + "/userExtend")
public class SysUserExtendApi {
    @Autowired
    private SysUserExtendService sysUserExtendService;

    /**
     * 根据用户Id查询,HSS 2018-07-05
     */
    @GetMapping("/findByUserId/{userId}")
    public SysUserExtendInfo findByUserId(@PathVariable("userId") Long userId) {
        return PojoConvertUtil.convert(sysUserExtendService.findByUserId(userId), SysUserExtendInfo.class);
    }
}
