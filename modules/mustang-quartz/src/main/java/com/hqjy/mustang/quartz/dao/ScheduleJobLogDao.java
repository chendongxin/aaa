package com.hqjy.mustang.quartz.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity, Long> {

}
