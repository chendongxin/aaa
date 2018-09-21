package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.dao.TransferCustomerInvalidDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerInvalidEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerInvalidService;
import com.hqjy.mustang.transfer.crm.service.TransferCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

public class TransferCustomerInvalidServiceImpl extends BaseServiceImpl<TransferCustomerInvalidDao, TransferCustomerInvalidEntity, Long> implements TransferCustomerInvalidService {

    @Autowired
    private TransferCustomerService transferCustomerService;

    /**
     * 设置客户无效
     */
    @Override
    @Transactional
    public R setCustomerInvalid(TransferCustomerDTO dto) {
        try {
            //添加无效客户记录
            super.save(new TransferCustomerInvalidEntity()
                    .setCreateUserId(getUserId())
                    .setCreateUserName(getUserName())
                    .setCustomerId(dto.getCustomerId())
                    .setStatus(dto.getStatus())
                    .setMemo(dto.getMemo()));
            TransferCustomerEntity customerEntity = transferCustomerService.findOne(dto.getCustomerId());
            //更新客户状态为无效状态
            customerEntity.setUpdateUserId(getUserId());
//            if (dto.getStatus() == 1) {
//                customerEntity.setStatus(Constant.CustomerStatus.FAILED_VALID.getValue());
//            } else {
//                customerEntity.setStatus(Constant.CustomerStatus.FAILED_INVALID.getValue());
//            }
//            (dto.getStatus() == 1)? customerEntity.setStatus(Constant.CustomerStatus.FAILED_VALID.getValue()) : customerEntity.setStatus(Constant.CustomerStatus.FAILED_INVALID.getValue());
            transferCustomerService.update(customerEntity);
            return R.ok();
        } catch (Exception e) {
            throw new RRException(StatusCode.BIZ_CUSTOMER_INVALID_EXCEPTION);
        }
    }

}
