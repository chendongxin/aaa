package com.hqjy.mustang.quartz.service;

/**
 * @author : heshuangshuang
 * @date : 2018/10/16 14:30
 */
public interface ResumerTaskService {

    /**
     * 简历抓取任务，指定时间之前
     */
    void resumeStartBefor(String hourStr);

    /**
     * 简历抓取任务
     */
    void resumeStart();
}
