package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCompanySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCompanyEntity;

import java.util.HashMap;
import java.util.List;

public interface TransferGenCompanyService extends BaseService<TransferGenCompanyEntity, Long> {

    /**
     * 获取所有推广公司列表
     */
    List<TransferGenCompanyEntity> getAllGenCompanyList();

    /**
     * 推广公司管理树数据
     */
    HashMap<String, List<TransferGenCompanyEntity>> getRecursionTree(boolean showRoot);

    /**
     * 获取除根节点外的推广公司
     */
    List<TransferGenCompanyEntity> getAllGenCompany();

}
