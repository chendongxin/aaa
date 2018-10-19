package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.dto.TransferGenWaySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;

import java.util.List;

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
     * 分页查询推广平台下的推广方式
     */
    List<TransferWaySourceEntity> findPageSource(PageQuery pageQuery);

}
