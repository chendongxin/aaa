package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerDetailDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerDetailService;
import org.springframework.stereotype.Service;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferCustomerDetailServiceImpl extends BaseServiceImpl<TransferCustomerDetailDao, TransferCustomerDetailEntity, Long> implements TransferCustomerDetailService {

    /**
     * 更新客户资料
     */
    @Override
    public int update(TransferCustomerDetailEntity customerDetail) {
        customerDetail.setUpdateUserId(getUserId());
        customerDetail.setUpdateUserName(getUserName());
        return baseDao.update(customerDetail);
    }

}
