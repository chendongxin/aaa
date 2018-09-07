package com.hqjy.mustang.transfer.crm.dao;

import com.hq.mustang.common.base.BaseDao;
import com.hqjy.mustang.transfer.crm.entity.TransferSmsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_sms 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferSmsDao extends BaseDao<TransferSmsEntity, Long> {
}