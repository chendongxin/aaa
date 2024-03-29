package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.isGeneralSeat;

/**
 * @author gmm
 */
@Service
public class TransferCustomerDetailServiceImpl extends BaseServiceImpl<TransferCustomerDetailDao, TransferCustomerDetailEntity, Long> implements TransferCustomerDetailService {

    private TransferCustomerDao transferCustomerDao;

    @Autowired
    public void setTransferCustomerDao(TransferCustomerDao transferCustomerDao) {
        this.transferCustomerDao = transferCustomerDao;
    }

    /**
     * 更新客户资料
     */
    @Override
    public int update(TransferCustomerDetailEntity customerDetail) {
        customerDetail.setUpdateUserId(getUserId());
        customerDetail.setUpdateUserName(getUserName());
        return baseDao.update(customerDetail);
    }


    @Override
    public TransferCustomerDetailEntity getCustomerDetailByCustomerId(Long customerId) {
        return baseDao.getCustomerDetailByCustomerId(customerId);
    }

    @Override
    public int updateCustomerDetail(TransferCustomerDetailEntity customerDetail) {
        if (isGeneralSeat()) {
            throw new RRException(StatusCode.USER_UNAUTHORIZED);
        }
        int countDetail = baseDao.update(baseDao.getCustomerDetailByCustomerId(customerDetail.getCustomerId())
                .setSex(customerDetail.getSex()).setAge(customerDetail.getAge()).setPositionApplied(customerDetail.getPositionApplied())
                .setApplyType(customerDetail.getApplyType()).setApplyKey(customerDetail.getApplyKey()).setWorkExperience(customerDetail.getWorkExperience())
                .setEducationId(customerDetail.getEducationId()).setMajor(customerDetail.getMajor()).setSchool(customerDetail.getSchool())
                .setWorkingPlace(customerDetail.getWorkingPlace()).setGraduateDate(customerDetail.getGraduateDate()).setNote(customerDetail.getNote())
        );
        int count = transferCustomerDao.update(transferCustomerDao.getCustomerByCustomerId(customerDetail.getCustomerId())
                .setGetWay(customerDetail.getGetWay()).setName(customerDetail.getName())
        );
        return countDetail > 0 && count > 0 ? countDetail : -1;
    }

}
