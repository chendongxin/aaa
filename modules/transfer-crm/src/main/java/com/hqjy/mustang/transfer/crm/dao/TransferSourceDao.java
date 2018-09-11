package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_source 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferSourceDao extends BaseDao<TransferSourceEntity, Long> {
    /**
     * 通过名称查询一条来源平台
     */
    TransferSourceEntity findOneByName(String name);
}