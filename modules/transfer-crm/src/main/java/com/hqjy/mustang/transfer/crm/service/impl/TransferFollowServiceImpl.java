package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.dao.TransferFollowDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import com.hqjy.mustang.transfer.crm.service.TransferFollowService;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferFollowServiceImpl  extends BaseServiceImpl<TransferFollowDao, TransferFollowEntity, Long> implements TransferFollowService {

    @Autowired
    private TransferProcessService transferProcessService;
    @Autowired
    private TransferCustomerDao transferCustomerDao;
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

    @Override
    @Transactional(rollbackFor = RRException.class)
    public int save(TransferFollowEntity entity) {
        TransferProcessEntity process = transferProcessService.getProcessByCustIdAndUserId(entity.getCustomerId());
        if (null == process) {
            throw new RRException(StatusCode.BIZ_FOLLOW_NOT_ALLOW_SAVE);
        }
        entity.setProcessId(process.getProcessId());
        entity.setCreateUserId(getUserId());
        entity.setCreateUserName(getUserName());
        int i = baseDao.save(entity);
        if( i < 0) {
            throw new RRException(StatusCode.BIZ_FOLLOW_SAVE_FAULT);
        }
        //更新流程的跟进部门信息
        process.setFollowCount(process.getFollowCount()+1);
        process.setLastFollowId(entity.getFollowId());
        process.setExpireTime(DateUtils.addDays(new Date(), 3));
        int update = transferProcessService.update(process);
        if( update < 0) {
            throw new RRException(StatusCode.BIZ_FOLLOW_UPDATE_PROCESS_FAULT);
        }
        if (process.getFollowCount() == 1) {
            transferCustomerDao.getCustomerByCustomerId(entity.getCustomerId()).setFirstUserId(getUserId()).setFirstUserName(getUserName());
        }
        transferCustomerDao.getCustomerByCustomerId(entity.getCustomerId()).setLastUserId(getUserId()).setLastUserName(getUserName());
        return update;
    }
}
