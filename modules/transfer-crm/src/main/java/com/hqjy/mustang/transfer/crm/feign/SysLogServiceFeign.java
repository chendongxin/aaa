package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.transfer.crm.model.entity.SysLogEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author gmm
 * @date 2018年11月5日13:00:46
 */
@FeignClient(name = "mustang-admin")
public interface SysLogServiceFeign {

    /**
     * 保存日志
     */
    @PostMapping(Constant.API_PATH + "/log/saveLog")
    int save(SysLogEntity sysLogEntity);

}
