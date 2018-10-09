package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xyq 2018年10月9日09:57:18
 */
public interface TransferCustomerReservationService extends BaseService<TransferCustomerReservationEntity, Long> {

    /**
     * 预约客户
     *
     * @param reservationEntity 请求参数
     * @return 返回处理结果
     */
    R reserveCustomer(TransferCustomerReservationEntity reservationEntity);

    /**
     * 转商机到NC
     * @param appointmentEntity 预约单对象
     * @return 返回处理结果
     */
    R transferToNc(TransferCustomerReservationEntity appointmentEntity);


    /**
     * 根据客户ID获取（已上门de）预约单记录
     *
     * @param customerId 客户ID
     * @return 返回结果集
     * @author xyq 2018年10月9日17:59:11
     */
    List<TransferCustomerReservationEntity> getReservationByCustomerId(Long customerId);
}
