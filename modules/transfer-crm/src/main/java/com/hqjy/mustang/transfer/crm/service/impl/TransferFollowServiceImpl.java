package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferFollowDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;
import com.hqjy.mustang.transfer.crm.service.TransferFollowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferFollowServiceImpl  extends BaseServiceImpl<TransferFollowDao, TransferFollowEntity, Long> implements TransferFollowService {

    /**
     * (批量)跟进客户ID获取最新的跟进记录
     *
     * @param customerIds 客户ID
     * @return 结果
     * @author xyq 2018年8月20日09:49:38
     */
    @Override
    public List<TransferFollowEntity> getLatestByCustomerIdBatch(String customerIds) {
        return baseDao.getLatestByCustomerIdBatch(customerIds);
    }
}
