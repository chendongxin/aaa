package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
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
     * 分页查询推广公司下的推广平台
     */
    List<TransferCompanySourceEntity> findPageSource(PageQuery pageQuery);

    /**
     * 保存推广公司下的推广平台
     */
    int saveCompanySource(TransferCompanySourceEntity transferCompanySourceEntity);
}
