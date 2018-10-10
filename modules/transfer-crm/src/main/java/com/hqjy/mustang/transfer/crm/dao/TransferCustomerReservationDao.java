package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerReservationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_customer_reservation 持久化层
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerReservationDao extends BaseDao<TransferCustomerReservationEntity, Long> {

    /**
     * 根据客户ID获取（已上门de）预约单记录
     *
     * @param customerId 客户ID
     * @return 返回结果集
     * @author xyq 2018年10月9日17:59:11
     */
    List<TransferCustomerReservationEntity> getReservationByCustomerId(@Param("customerId") Long customerId);
}