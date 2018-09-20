package com.hqjy.mustang.quartz.util;

import com.google.gson.Gson;
import com.hqjy.mustang.common.base.utils.SpringContextUtils;
import com.hqjy.mustang.quartz.entity.ScheduleJobEntity;
import com.hqjy.mustang.quartz.entity.ScheduleJobLogEntity;
import com.hqjy.mustang.quartz.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 *
 * @author : heshuangshuang
 * @date : 2018/9/20 11:22
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jsonJob = context.getMergedJobDataMap().getString(ScheduleJobEntity.JOB_PARAM_KEY);
        ScheduleJobEntity scheduleJob = new Gson().fromJson(jsonJob, ScheduleJobEntity.class);

        //获取scheduleJobLogService
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity jobLogEntity = new ScheduleJobLogEntity();
        jobLogEntity.setJobId(scheduleJob.getJobId());
        jobLogEntity.setBeanName(scheduleJob.getBeanName());
        jobLogEntity.setMethodName(scheduleJob.getMethodName());
        jobLogEntity.setParams(scheduleJob.getParams());
        jobLogEntity.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogEntity.setTimes((int) times);
            //任务状态    0：成功    1：失败
            jobLogEntity.setStatus(0);

            log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLogEntity.setTimes((int) times);

            //任务状态    0：成功    1：失败
            jobLogEntity.setStatus(1);
            jobLogEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(jobLogEntity);
        }
    }
}
