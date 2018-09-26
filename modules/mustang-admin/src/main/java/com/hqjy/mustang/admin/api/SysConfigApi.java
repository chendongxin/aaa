package com.hqjy.mustang.admin.api;

import com.alibaba.fastjson.JSONObject;
import com.hqjy.mustang.admin.model.entity.SysConfigEntity;
import com.hqjy.mustang.admin.model.entity.SysConfigInfoEntity;
import com.hqjy.mustang.admin.service.SysConfigService;
import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.validator.RestfulValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

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
        return Optional.ofNullable(sysConfigService.getConfig(code)).map(JSONObject::parse).orElse(null);
    }
}
