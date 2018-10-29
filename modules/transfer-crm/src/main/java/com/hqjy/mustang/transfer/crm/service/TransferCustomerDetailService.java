package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;

/**
 * @author gmm
 */
public interface TransferCustomerDetailService extends BaseService<TransferCustomerDetailEntity, Long> {

    /**
     * 根据客户ID查找一条记录
     *
     * @param customerId 客户ID
     * @return TransferCustomerDetailEntity
     */
    TransferCustomerDetailEntity getCustomerDetailByCustomerId(Long customerId);

    /**
     * 修改客户资料
     *
     * @param customerDetail 客户资料明细
     * @return 修改结果
     */
    int updateCustomerDetail(TransferCustomerDetailEntity customerDetail);
}
