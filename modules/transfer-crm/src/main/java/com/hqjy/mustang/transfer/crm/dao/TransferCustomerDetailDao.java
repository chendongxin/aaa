package com.hqjy.mustang.transfer.crm.dao;

import com.hq.mustang.common.base.BaseDao;
import com.hqjy.mustang.transfer.crm.entity.TransferCustomerDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_detail 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferCustomerDetailDao extends BaseDao<TransferCustomerDetailEntity, Long> {
}