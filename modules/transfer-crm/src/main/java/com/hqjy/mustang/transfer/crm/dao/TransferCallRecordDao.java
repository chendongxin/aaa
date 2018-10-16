package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCallRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_call_record 持久化层
 * 
 * @author : xyq
 * @date : 2018/10/11 16:16
 */
@Mapper
public interface TransferCallRecordDao extends BaseDao<TransferCallRecordEntity, Long> {
}