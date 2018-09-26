package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferFollowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_follow 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferFollowDao extends BaseDao<TransferFollowEntity, Long> {

    /**
     * (批量)跟进客户ID获取最新的跟进记录
     *
     * @param customerIds 客户ID
     * @return 结果
     * @author xyq 2018年8月20日09:49:38
     */
    List<TransferFollowEntity> getLatestByCustomerIdBatch(@Param("customerIds")String customerIds);
}