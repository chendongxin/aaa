package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;

import java.util.List;

public interface TransferCompanySourceService extends BaseService<TransferCompanySourceEntity, Long> {


    /**
     * 分页查询推广公司下的推广平台
     */
    List<TransferCompanySourceEntity> findPageSource(PageQuery pageQuery);
}
