package com.hqjy.mustang.transfer.crm.feign;

import com.hqjy.mustang.transfer.crm.model.entity.LogMessageQueueEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xyq
 * @date create on 2018/10/8
 * @apiNote
 */
@FeignClient(name = "mustang-admin")
public interface LogMessageQueueFeign {

    /**
     * 保存MQ队列消费日志
     *
     * @param entity 消息队列实体对象
     * @author xyq
     * @date 2018年10月8日14:39:08
     */
    @GetMapping("/save")
    void save(@RequestBody LogMessageQueueEntity entity);
}
