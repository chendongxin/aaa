package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.DateUtils;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.model.admin.SysDeptInfo;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferFollowDao;
import com.hqjy.mustang.transfer.crm.feign.SysDeptServiceFeign;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import com.hqjy.mustang.transfer.crm.service.TransferFollowService;
import com.hqjy.mustang.transfer.crm.service.TransferProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isGeneralSeat;

/**
 * @author gmm
 * @apiNote 跟进记录业务层
 */
@Service
public class TransferFollowServiceImpl extends BaseServiceImpl<TransferFollowDao, TransferFollowEntity, Long> implements TransferFollowService {

    private TransferProcessService transferProcessService;
    private TransferCustomerService transferCustomerService;
    private TransferCustomerDao transferCustomerDao;
    private SysDeptServiceFeign sysDeptServiceFeign;

    @Autowired
    public void setTransferProcessService(TransferProcessService transferProcessService) {
        this.transferProcessService = transferProcessService;
    }

    @Autowired
    public void setSysDeptServiceFeign(SysDeptServiceFeign sysDeptServiceFeign) {
        this.sysDeptServiceFeign = sysDeptServiceFeign;
    }

    @Autowired
    public void setTransferCustomerService(TransferCustomerService transferCustomerService) {
        this.transferCustomerService = transferCustomerService;
    }

    @Autowired
    public void setTransferCustomerDao(TransferCustomerDao transferCustomerDao) {
        this.transferCustomerDao = transferCustomerDao;
    }

    @Override
    @Transactional(rollbackFor = RRException.class)
    public int save(TransferFollowEntity entity) {
        Date time = new Date();
        TransferProcessEntity process = transferProcessService.getProcessByCustIdAndUserId(entity.getCustomerId());
        if (null == process) {
            throw new RRException(StatusCode.BIZ_FOLLOW_NOT_ALLOW_SAVE);
        }
        TransferCustomerEntity transferCustomerEntity = transferCustomerDao.getCustomerByCustomerId(entity.getCustomerId());
        int status = transferCustomerEntity.getStatus();
        if (status != 0 && status != 3) {
            throw new RRException(StatusCode.BIZ_FOLLOW_NOT_POTENTIAL_RESERVATION);
        }
        entity.setProcessId(process.getProcessId());
        entity.setCreateUserId(getUserId());
        entity.setCreateUserName(getUserName());
        int i = baseDao.save(entity);
        if (i < 0) {
            throw new RRException(StatusCode.BIZ_FOLLOW_SAVE_FAULT);
        }
        //更新流程的跟进部门信息
        process.setFollowCount(process.getFollowCount() + 1);
        process.setLastFollowId(entity.getFollowId());
        process.setExpireTime(DateUtils.addDays(time, 15));
        int update = transferProcessService.update(process);
        if (update < 0) {
            throw new RRException(StatusCode.BIZ_FOLLOW_UPDATE_PROCESS_FAULT);
        }
        List<SysDeptInfo> sysDeptInfoList = sysDeptServiceFeign.getUserDeptList(getUserId());
        if (process.getFollowCount() == 1) {
            transferCustomerEntity.setFirstUserId(getUserId()).setFirstUserName(getUserName()).setFirstUserDeptId(sysDeptInfoList.get(0).getDeptId());
        }
        transferCustomerEntity.setLastUserId(getUserId()).setLastUserName(getUserName()).setLastUserDeptId(sysDeptInfoList.get(0).getDeptId())
                .setUpdateUserId(getUserId()).setUpdateUserName(getUserName()).setUpdateTime(time).setLastFollowTime(time);
        update = transferCustomerService.update(transferCustomerEntity);
        if (update < 0) {
            throw new RRException(StatusCode.BIZ_CUSTOMER_UPDATE_FAULT);
        }
        return update;
    }

    @Override
    public List<TransferFollowEntity> findPage(PageQuery pageQuery) {
        if (isGeneralSeat()) {
            pageQuery.put("userId", getUserId());
        }
        return super.findPage(pageQuery);
    }
}
