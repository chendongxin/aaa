package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.model.entity.SysLogEntity;
import com.hqjy.mustang.admin.service.SysLogService;
import com.hqjy.mustang.common.base.constant.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 日志信息
 *
 * @author : gmm
 * @date : 2018/11/5 16:31
 */
@RestController
@RequestMapping(Constant.API_PATH + "/log")
public class SysLogApi {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 保存商机回收日志
     */
    @ApiOperation(value = "保存商机回收日志")
    @PostMapping(value = "/saveLog")
    public int save(SysLogEntity sysLogEntity) {
        return sysLogService.save(sysLogEntity);
    }
}
