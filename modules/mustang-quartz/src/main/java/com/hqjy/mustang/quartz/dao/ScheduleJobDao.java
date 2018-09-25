package com.hqjy.mustang.quartz.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.quartz.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity, Long> {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);
}
