package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.utils.R;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author xyq
 */
@FeignClient(name = "mustang-admin")
public interface UserFeignClient {
    /**
     * 获取当前用户信息
     *
     * @return 返回查询结果
     */
    @GetMapping
    R getUser();
}
