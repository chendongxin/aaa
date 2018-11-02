package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferCompanySourceDao;
import com.hqjy.mustang.transfer.crm.dao.TransferGenCostDao;
import com.hqjy.mustang.transfer.crm.dao.TransferWaySourceDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCostEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenCostServiceImpl extends BaseServiceImpl<TransferGenCostDao, TransferGenCostEntity, Long> implements TransferGenCostService {

    @Autowired
    private TransferCompanySourceDao transferCompanySourceDao;
    @Autowired
    private TransferWaySourceDao transferWaySourceDao;

    /**
     * 增加一个推广费用
     */
    @Override
    public int save(TransferGenCostEntity transferGenCostEntity) {
        transferGenCostEntity.setCreateUserId(getUserId());
        transferGenCostEntity.setCreateUserName(getUserName());
        TransferCompanySourceEntity companySource = transferCompanySourceDao.findByCompanyIdAndSourceId(transferGenCostEntity.getCompanyId(), transferGenCostEntity.getSourceId());
        companySource.setSign(1);
        transferCompanySourceDao.update(companySource);
        TransferWaySourceEntity waySource = transferWaySourceDao.findByWayIdAndSourceId(transferGenCostEntity.getWayId(), transferGenCostEntity.getSourceId());
        waySource.setSign(1);
        transferWaySourceDao.update(waySource);
        return baseDao.save(transferGenCostEntity);
    }
}
