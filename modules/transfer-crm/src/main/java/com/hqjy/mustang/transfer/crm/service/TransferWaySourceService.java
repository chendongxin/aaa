package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;

public interface TransferWaySourceService extends BaseService<TransferWaySourceEntity, Long> {

    /**
     * 删除指定平台下的推广方式
     */
    int deleteBatch(Long[] ids);

    /**
     * 保存推广平台下的推广方式
     */
    int saveWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO);

    /**
     * 修改推广平台下的推广方式
     */
    int updateWaySource(TransferGenWaySourceDTO transferGenWaySourceDTO);
}
