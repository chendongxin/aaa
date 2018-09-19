package com.hqjy.mustang.transfer.crawler.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crawler.model.entity.TransferResumeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_resume 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/11 09:06
 */
@Mapper
public interface TransferResumeDao extends BaseDao<TransferResumeEntity, Long> {
    /**
     * 查询数据库中最后一条记录
     */
    TransferResumeEntity findLast();
}