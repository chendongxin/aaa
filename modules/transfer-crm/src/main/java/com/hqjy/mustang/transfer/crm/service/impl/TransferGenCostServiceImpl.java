package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferGenCostDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCostEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenCostService;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenCostServiceImpl extends BaseServiceImpl<TransferGenCostDao, TransferGenCostEntity, Long> implements TransferGenCostService {

    /**
     * 增加一个推广费用
     */
    @Override
    public int save(TransferGenCostEntity transferGenCostEntity) {
        transferGenCostEntity.setCreateUserId(getUserId());
        transferGenCostEntity.setCreateUserName(getUserName());
        return baseDao.save(transferGenCostEntity);
    }
}
