package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;

public interface TransferCustomerReservationService extends BaseService<TransferCustomerReservationEntity, Long> {

    /**
     * 预约客户
     *
     * @param reservationEntity 请求参数
     * @return 返回处理结果
     */
    R reserveCustomer(TransferCustomerReservationEntity reservationEntity);
}
