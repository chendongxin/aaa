package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;

import java.util.List;

public interface TransferFollowService extends BaseService<TransferFollowEntity, Long> {

    /**
     * (批量)跟进客户ID获取最新的跟进记录
     *
     * @param customerIds 客户ID
     * @return 结果
     * @author xyq 2018年8月20日09:49:38
     */
    List<TransferFollowEntity> getLatestByCustomerIdBatch(String customerIds);
}
