package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;

import java.util.List;


public interface TransferGenWayService extends BaseService<TransferGenWayEntity, Long> {

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
     * 修改推广方式
     */
    int update(TransferGenWaySourceDTO genWaySourceDTO);
}
