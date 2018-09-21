package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_detail 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerDetailDao extends BaseDao<TransferCustomerDetailEntity, Long> {

    /**
     * 根据客户ID查询一条记录
     */
    TransferCustomerDetailEntity getCustomerDetailByCustomerId(Long customerId);
}