package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_company_source 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferCompanySourceDao extends BaseDao<TransferCompanySourceEntity, Long> {
}