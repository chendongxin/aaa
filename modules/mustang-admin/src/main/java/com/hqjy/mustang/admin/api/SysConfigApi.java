package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.SysConfigService;
import com.hqjy.mustang.common.base.constant.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:39
 */
@RestController
@RequestMapping(Constant.API_PATH + "/config")
public class SysConfigApi {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 根据配置code获取配置值列表
     */
    @GetMapping("/info/{code}")
    public Object getInfoList(@PathVariable("code") String code) {
        return sysConfigService.getConfig(code);
    }

    /**
     * 根据code获取系统配置信息
     */
    @ApiOperation(value = "根据code获取系统配置信息", notes = "根据code获取系统配置信息")
    @GetMapping(value = "/get/config")
    public String getConfig(String code) {
        return sysConfigService.getConfig(code);
    }

}
