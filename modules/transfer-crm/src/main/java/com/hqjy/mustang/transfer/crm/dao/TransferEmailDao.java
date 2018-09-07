package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.entity.TransferEmailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_email 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferEmailDao extends BaseDao<TransferEmailEntity, Integer> {
}