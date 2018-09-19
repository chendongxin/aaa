package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;

import java.util.List;

public interface TransferSourceService extends BaseService<TransferSourceEntity, Long> {

    List<TransferSourceEntity> findNotByCompanyId(Long companyId);

}
