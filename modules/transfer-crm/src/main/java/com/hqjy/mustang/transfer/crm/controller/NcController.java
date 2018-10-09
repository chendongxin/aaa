package com.hqjy.mustang.transfer.crm.controller;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.service.impl.NcServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xieyuqing
 * @ description NC交互接口
 * @ date create in 2018年9月12日14:59:48
 */
@Api(tags = "系统与NC交互接口")
@RestController
@RequestMapping("/nc")
public class NcController {

    private NcServiceImpl ncRestTemplate;

    @Autowired
    public void setRestTemplate(NcServiceImpl ncRestTemplate) {
        this.ncRestTemplate = ncRestTemplate;
    }


    @ApiOperation(value = "根据系统部门获取NC校区老师", notes = "根据系统部门获取NC校区老师,参数名key：(部门)name，value值例：衡阳江东校区")
    @GetMapping("/getNcTeacher")
    public R getNcTeacher(@RequestParam("name") String name) {
        return R.result(ncRestTemplate.requestNCGetTeacher(name));
    }
}
