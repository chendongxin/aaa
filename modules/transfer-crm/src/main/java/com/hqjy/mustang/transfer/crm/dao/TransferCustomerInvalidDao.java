package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerInvalidEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_invalid 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerInvalidDao extends BaseDao<TransferCustomerInvalidEntity, Long> {

    /**
     * 通过customerId查找一条记录
     */
    TransferCustomerInvalidEntity getCustomerByCustomerId(Long customerId);
}