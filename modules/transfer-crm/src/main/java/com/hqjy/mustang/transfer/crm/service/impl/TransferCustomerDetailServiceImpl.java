package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDao;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDetailDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferCustomerDetailServiceImpl extends BaseServiceImpl<TransferCustomerDetailDao, TransferCustomerDetailEntity, Long> implements TransferCustomerDetailService {

    @Autowired
    private TransferCustomerDetailDao transferCustomerDetailDao;
    @Autowired
    private TransferCustomerDao transferCustomerDao;

    /**
     * 更新客户资料
     */
    @Override
    public int update(TransferCustomerDetailEntity customerDetail) {
        customerDetail.setUpdateUserId(getUserId());
        customerDetail.setUpdateUserName(getUserName());
        return baseDao.update(customerDetail);
    }

    /**
     * 根据客户ID查找一条记录
     * @param customerId
     * @return TransferCustomerDetailEntity
     */
    @Override
    public TransferCustomerDetailEntity getCustomerDetailByCustomerId(Long customerId) {
        return baseDao.getCustomerDetailByCustomerId(customerId);
    }

    @Override
    public int updateCustomerDetail(TransferCustomerDetailEntity customerDetail) {
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
