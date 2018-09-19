package com.hqjy.mustang.transfer.call.service;

import com.hqjy.mustang.common.model.admin.SysUserExtendInfo;
import com.hqjy.mustang.transfer.call.model.dto.TqCallClienIdDTO;
import com.hqjy.mustang.transfer.call.model.dto.TqCallRecordDTO;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/18 16:12
 */
public interface TqApiService {

    /**
     * 获取外呼token
     */
    String getCallToken(SysUserExtendInfo userExtendInfo);

    /**
     * 执行拨打电话
     */
    boolean clickCall(int kefuUin, String token, String phone, String params);

    /**
     * 获取业务接口token
     */
    String getAccessToken();

    /**
     * 分页获取通话记录
     */
    List<TqCallRecordDTO> getPhoneRecord(int pageSize, int pageNum, long startTime, long endTime);

}
