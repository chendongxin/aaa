package com.hqjy.mustang.allot.dao;

import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerDetailEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_detail 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/17 18:33
 */
@Mapper
public interface TransferAllotCustomerDetailDao extends BaseDao<TransferAllotCustomerDetailEntity, Long> {
    void saveCustomer(TransferAllotCustomerEntity customer);
}