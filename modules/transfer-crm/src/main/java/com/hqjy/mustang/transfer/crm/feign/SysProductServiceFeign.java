package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author gmm
 * @date 2018年11月5日13:00:46
 */
@FeignClient(name = "mustang-admin")
public interface SysProductServiceFeign {

    /**
     * 根据赛道Id查询赛道名称
     */
    @GetMapping(Constant.API_PATH + "/product/getName/{productId}")
    String findByProductId(@PathVariable("productId") Long productId);

    /**
     * 获取电销用户的赛道
     */
    @GetMapping(Constant.API_PATH + "/product/getProList/{userId}")
    List<Long> findByUserId(@PathVariable("userId") Long userId);
}
