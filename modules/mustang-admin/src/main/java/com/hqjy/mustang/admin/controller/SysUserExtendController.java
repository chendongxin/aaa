package com.hqjy.mustang.admin.controller;

import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.utils.PageInfo;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 人员映射
 */
@Api(tags = "人员映射", description = "SysUserExtendController")
@RestController
@RequestMapping("/userExtend")
public class SysUserExtendController {
    @Autowired
    private SysUserExtendService sysUserExtendService;

    /**
     * 人员映射信息分页
     */
    @ApiOperation(value = "人员映射分页查询", notes = "人员映射分页查询")
    @RequestMapping(value = "/listPage", method = {RequestMethod.GET, RequestMethod.POST})
    public R list(@RequestParam HashMap<String, Object> pageParam, @RequestBody(required = false) HashMap<String, Object> queryParam){
        return R.ok(new PageInfo<>(sysUserExtendService.findPage(PageQuery.build(pageParam, queryParam))));
    }


}
