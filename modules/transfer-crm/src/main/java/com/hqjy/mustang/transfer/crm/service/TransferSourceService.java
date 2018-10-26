package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;

import java.util.HashMap;
import java.util.List;

public interface TransferSourceService extends BaseService<TransferSourceEntity, Long> {

    /**
     * 获取不属于指定公司的推广平台
     */
    List<TransferSourceEntity> findNotByCompanyId(Long companyId);

    /**
     * 获取属于指定公司的推广平台
     */
    List<TransferSourceEntity> findByCompanyId(Long companyId);

    /**
     * 获取所有推广平台列表
     */
    List<TransferSourceEntity> getAllSourceList();

    /**
     * 推广平台管理树数据
     */
    HashMap<String, List<TransferSourceEntity>> getRecursionTree(boolean showRoot);

    /**
     * 根据邮箱后缀查询来源
     */
    TransferSourceEntity findByEmailDomain(String emailDomain);

}
