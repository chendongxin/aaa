package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * transfer_gen_way 持久化层
 *
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Component
@Mapper
public interface TransferGenWayDao extends BaseDao<TransferGenWayEntity, Long> {

    /**
     * 获取所有推广方式列表
     *
     * @return
     */
    List<TransferGenWayEntity> getAllGenWayList();

    /**
     * 通过名称查询推广方式
     *
     * @param genWay
     * @return
     */
    TransferGenWayEntity findOneByGenName(String genWay);

    /**
     * 获取父ID下对应推广方式
     *
     * @param parentId
     * @return
     */
    List<TransferGenWayEntity> findByParentId(Long parentId);

    /**
     * 查询指定推广平台下的推广方式
     *
     * @param sourceId
     * @return
     */
    List<TransferGenWayEntity> findBySourceId(Long sourceId);

    /**
     * 查询不在指定推广平台下的推广方式
     *
     * @param sourceId
     * @return
     */
    List<TransferGenWayEntity> findNotBySourceId(Long sourceId);
}