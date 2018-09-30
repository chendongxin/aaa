package com.hqjy.mustang.transfer.call.service;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:45
 */
public interface TqCallService {
    /**
     * TQ 外呼
     */
    boolean callOut(Long customerId, String phone);

    /**
     * 同步通话记录
     */
    void syncRecord(long startTime);
}
