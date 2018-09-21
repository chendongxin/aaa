package com.hqjy.mustang.quartz.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.quartz.dao.ScheduleJobDao;
import com.hqjy.mustang.quartz.entity.ScheduleJobEntity;
import com.hqjy.mustang.quartz.service.ScheduleJobService;
import com.hqjy.mustang.quartz.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobDao, ScheduleJobEntity, Long> implements ScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = baseDao.findList(new HashMap<>());
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        int count = baseDao.save(scheduleJob);
        if (count > 0) {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScheduleJobEntity scheduleJob) {
        int count = baseDao.update(scheduleJob);
        if (count > 0) {
            ScheduleUtils.updateScheduleJob(scheduler, baseDao.findOne(scheduleJob.getJobId()));
        }
        return count;
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", status);
        return baseDao.updateBatch(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, baseDao.findOne(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }

    @Override
    public int delete(Long jobId) {
        //删除数据
        int count = super.delete(jobId);
        if (count > 0) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }
        return count;
    }
}
