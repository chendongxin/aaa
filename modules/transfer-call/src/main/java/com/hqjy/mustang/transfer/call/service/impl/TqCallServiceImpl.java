package com.hqjy.mustang.transfer.call.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.Tools;
import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.call.service.TqApiService;
import com.hqjy.mustang.transfer.call.service.TqCallService;
import com.hqjy.mustang.transfer.call.fegin.SysUserExtendApiService;
import com.hqjy.mustang.transfer.call.model.dto.TqCallClienIdDTO;
import com.hqjy.mustang.transfer.call.model.dto.TqCallRecordDTO;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import com.hqjy.mustang.transfer.call.service.TransferCallRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:45
 */
@Service
@Slf4j
public class TqCallServiceImpl implements TqCallService {

    @Autowired
    private SysUserExtendApiService sysUserExtendApiService;

    @Autowired
    private TqApiService tqApiService;

    @Autowired
    private TransferCallRecordService transferCallRecordService;
    /**
     * 线程池对象，单例
     */
    private static ExecutorService singleThreadPool;

    @PostConstruct
    public void init() {
        //创建线程池对象
        if (singleThreadPool == null) {
            //自定义线程名称
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("syncRecord-pool-%d").build();
            //当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
            long keepActiveTime = 60;
            //设置时间单位，秒
            TimeUnit timeUnit = TimeUnit.SECONDS;
            //线程池最大队列
            int capacity = 10240;
            //线程池最大能接受多少线程
            int maximumPoolSize = 1;
            //核心池大小
            int corePoolSize = 1;
            singleThreadPool = new java.util.concurrent.ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,
                    new LinkedBlockingQueue<>(capacity), namedThreadFactory, new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
            log.info("同步通话记录线程池创建成功[核心池大小:" + corePoolSize + ";最大线程：" + maximumPoolSize + ";生命周期:" + keepActiveTime + "]");
        }
    }


    /**
     * TQ 外呼
     */
    @Override
    public boolean callOut(Long customerId, String phone) {
        Long userId = ShiroUtils.getUserId();
        String userName = ShiroUtils.getUserName();
        // 查询用户绑定的tq帐号信息
        SysUserExtendInfo userExtendInfo = sysUserExtendApiService.findByUserId(userId);
        // 请求token
        String token = tqApiService.getCallToken(userExtendInfo);
        String params = Tools.base64Encode(JsonUtil.toJson(new TqCallClienIdDTO().setCustomerId(customerId).setUserId(userId).setUserName(userName)));
        return tqApiService.clickCall(userExtendInfo.getTqId(), token, phone, params);
    }


    /**
     * 同步通话记录,线程池控制同步任务，避免同步相同数据
     */
    @Override
    public void syncRecord(long start) {
        singleThreadPool.execute(() -> {
            long startTime = start;
            // 查询上次同步时间
            TransferCallRecordEntity lastCallRecord = transferCallRecordService.findLast();
            if (lastCallRecord != null) {
                // 如果存在，开始时间为插入数据库的结束时间，达到继续同步的目的
                startTime = lastCallRecord.getInsertDbTime();
            }
            int pageSize = 1000;
            int pageNum = 1;
            // 同步结束时间为当前时间
            long endTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            List<TqCallRecordDTO> list = tqApiService.getPhoneRecord(pageSize, pageNum, startTime, endTime);
            while (list.size() > 0) {
                for (TqCallRecordDTO tqCallRecordDTO : list) {
                    // 写入数据库
                    transferCallRecordService.save(tqCallRecordDTO);
                }
                pageNum++;
                list = tqApiService.getPhoneRecord(pageSize, pageNum, startTime, endTime);
            }
        });

    }
}
