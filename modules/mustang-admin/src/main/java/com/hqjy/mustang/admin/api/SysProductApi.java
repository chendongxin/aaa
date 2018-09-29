package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.SysProductService;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.utils.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gmm
 * @date create on 2018/9/20
 */
@RestController
@RequestMapping(Constant.API_PATH)
@Slf4j
public class SysProductApi {

    @Autowired
    private SysProductService sysProductService;

    /**
     * 获取所有赛道
     */
    @ApiOperation(value = "获取所有赛道信息", notes = "获取所有赛道信息")
    @GetMapping(value = "/product/all")
    public R getAllProduct() {
        return R.ok(sysProductService.getAllProductList());
    }

}
