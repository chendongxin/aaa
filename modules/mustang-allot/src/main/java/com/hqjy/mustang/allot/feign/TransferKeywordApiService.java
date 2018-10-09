package com.hqjy.mustang.allot.feign;

import com.hqjy.mustang.allot.feign.impl.TransferKeywordApiServiceFallbackImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.model.admin.SysUserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 查询用户信息
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/29 21:55
 */
@FeignClient(name = "mustang-crm", fallback = TransferKeywordApiServiceFallbackImpl.class)
public interface TransferKeywordApiService {

    /**
     * 根据职位信息，遍历出关键字
     */
    @GetMapping(value = Constant.API_PATH + "/keyword/getKeyword")
    String getKeyword(@RequestParam("info") String info);

}
