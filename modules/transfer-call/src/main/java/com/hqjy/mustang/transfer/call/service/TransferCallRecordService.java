package com.hqjy.mustang.transfer.call.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.call.model.dto.CallStatisDTO;
import com.hqjy.mustang.transfer.call.model.dto.TqCallRecordDTO;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;

/**
 * @author : heshuangshuang
 * @date : 2018/9/7 16:45
 */
public interface TransferCallRecordService extends BaseService<TransferCallRecordEntity, Long> {
    /**
     * 保存同步的通话记录
     */
    int save(TqCallRecordDTO tqCallRecordDTO);

    /**
     * 查询数据库中最后一条记录
     */
    TransferCallRecordEntity findLast();

    /**
     * 个人首页通话记录统计
     *
     * @param type
     * @return
     */
    CallStatisDTO getPersonStatis( String type);


}
