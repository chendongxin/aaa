package com.hqjy.mustang.transfer.call.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.call.model.dto.CallStatisDTO;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * transfer_call_record 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/18 16:01
 */
@Mapper
public interface TransferCallRecordDao extends BaseDao<TransferCallRecordEntity, Long> {
    /**
     * 查询数据库中最后一条记录
     */
    TransferCallRecordEntity findLast();

    /**
     * 统计个人首页通话记录
     * @param createuserId
     * @param type
     * @return
     */
    CallStatisDTO statisPerson(@Param("createuserId") Long createuserId, @Param("type") String type);
}