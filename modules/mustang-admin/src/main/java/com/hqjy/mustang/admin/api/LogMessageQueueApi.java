package com.hqjy.mustang.admin.api;

import com.hqjy.mustang.admin.model.entity.LogMessageQueueEntity;
import com.hqjy.mustang.admin.service.LogMessageQueueService;
import com.hqjy.mustang.common.base.constant.Constant;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author xyq
 * @date create on 2018/10/8
 * @apiNote
 */
@RestController
@RequestMapping(Constant.API_PATH + "/queue")
@Slf4j
public class LogMessageQueueApi {

    private LogMessageQueueService logMessageQueueService;

    @Autowired
    public void setLogMessageQueueService(LogMessageQueueService logMessageQueueService) {
        this.logMessageQueueService = logMessageQueueService;
    }

    /**
     * @param entity 消息队列实体对象
     * @author xyq
     * @date 2018年10月8日14:39:08
     */
    @GetMapping("/save")
    @ApiOperation(value = "保存MQ队列消费日志", notes = "保存MQ队列消费日志")
    public void save(@RequestBody LogMessageQueueEntity entity) {
        logMessageQueueService.save(entity);
    }
}
