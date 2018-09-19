package com.hqjy.mustang.transfer.call.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.call.model.entity.TransferCallRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}