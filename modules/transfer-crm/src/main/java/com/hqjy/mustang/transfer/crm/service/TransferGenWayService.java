package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;

import java.util.List;

/**
 * @author gmm
 */
public interface TransferGenWayService extends BaseService<TransferGenWayEntity, Long> {


    /**
     * 保存推广平台下的推广方式
     */
    int saveWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO);

    /**
     * 获取不属于指定平台的推广方式
     */
    List<TransferGenWayEntity> findNotBySourceId(Long sourceId);

    /**
     * 获取指定来源平台的推广方式
     */
    List<TransferGenWayEntity> findBySourceId(Long sourceId);

    /**
     * 获取所有推广方式
     */
    List<TransferGenWayEntity> getAllGenWayList();

    /**
     * 修改推广平台下的推广方式
     */
    int update(TransferWaySourceEntity waySource);
}
