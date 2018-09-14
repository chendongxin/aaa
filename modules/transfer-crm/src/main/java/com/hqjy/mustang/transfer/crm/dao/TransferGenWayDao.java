package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_gen_way 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferGenWayDao extends BaseDao<TransferGenWayEntity, Long> {
    /**
     * 获取所有推广方式列表
     */
    List<TransferGenWayEntity> getAllGenWayList();

    /**
     * 通过名称查询推广方式
     */
    TransferGenWayEntity findOneByGenName(String genWay);

    /**
     * 获取父ID下对应推广方式
     */
    List<TransferGenWayEntity> findByParentId(Long parentId);
}