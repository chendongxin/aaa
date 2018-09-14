package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerRepeatEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_repeat 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/14 15:58
 */
@Mapper
public interface TransferCustomerRepeatDao extends BaseDao<TransferCustomerRepeatEntity, Long> {
}