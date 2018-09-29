package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferProcessDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferProcessServiceImpl extends BaseServiceImpl<TransferProcessDao, TransferProcessEntity, Long> implements TransferProcessService {

    /**
     * 获取当前流程为激活状态的流程数据
     *
     * @param customerId 客户ID
     * @return 返回流程对象
     */
    @Override
    public TransferProcessEntity getProcessByCustomerId(Long customerId) {
        return baseDao.getProcessByCustomerId(customerId);
    }

    /**
     * 设置客户流程过期
     *
     * @param entity 流程对象
     * @return 退回结果
     */
    @Override
    public int disableProcessActive(TransferProcessEntity entity) {
        return baseDao.disableProcessActive(entity);
    }

    @Override
    public List<TransferProcessEntity> getFirstAllotProcessBatch(String customerIds) {
        return baseDao.getFirstAllotProcessBatch(customerIds);
    }
}
