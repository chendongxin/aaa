package com.hqjy.mustang.quartz.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.quartz.dao.ScheduleJobLogDao;
import com.hqjy.mustang.quartz.entity.ScheduleJobLogEntity;
import com.hqjy.mustang.quartz.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity, Long> implements ScheduleJobLogService {

}
