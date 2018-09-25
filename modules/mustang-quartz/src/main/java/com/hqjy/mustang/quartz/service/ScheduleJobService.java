package com.hqjy.mustang.quartz.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.quartz.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
public interface ScheduleJobService extends BaseService<ScheduleJobEntity, Long> {

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);
}
