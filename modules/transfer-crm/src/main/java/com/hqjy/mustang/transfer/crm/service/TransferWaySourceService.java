package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;

import java.util.List;

/**
 * @author gmm
 */
public interface TransferWaySourceService extends BaseService<TransferWaySourceEntity, Long> {

    /**
     * 分页查询推广平台下的推广方式
     */
    List<TransferWaySourceEntity> findPageGenWay(PageQuery pageQuery);

    /**
     * 根据wayId和sourceId查询是否存在重复数据
     */
    TransferWaySourceEntity findByWayIdAndSourceId(Long wayId, Long sourceId);

}
