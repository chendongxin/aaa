package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.service.SysProductService;
import com.hqjy.mustang.admin.service.SysUserProService;
import com.hqjy.mustang.common.base.constant.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 赛道信息
 *
 * @author : gmm
 * @date : 2018/11/5 16:31
 */
@RestController
@RequestMapping(Constant.API_PATH + "/product")
public class SysProductApi {

    @Autowired
    private SysProductService sysProductService;
    @Autowired
    private SysUserProService sysUserProService;

    /**
     * 根据赛道Id查询赛道名称
     */
    @ApiOperation(value = "根据赛道Id查询赛道名称")
    @GetMapping(value = "/getName/{productId}")
    public String findByProductId(@PathVariable("productId") Long productId) {
        return sysProductService.findOne(productId).getName();
    }

    /**
     * 获取用户的赛道列表
     */
    @ApiOperation(value = "获取用户的赛道列表")
    @GetMapping(value = "/getProList/{userId}")
    public List<Long> findByUserId(@PathVariable("userId") Long userId) {
        return sysUserProService.getUserProId(userId);
    }
}
