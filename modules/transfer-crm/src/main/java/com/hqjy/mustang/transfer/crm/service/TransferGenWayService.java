package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;

import java.util.HashMap;
import java.util.List;

public interface TransferGenWayService extends BaseService<TransferGenWayEntity, Long> {

    /**
     * 获取所有推广公司列表
     */
    List<TransferGenWayEntity> getAllGenWayList();

    /**
     * 推广公司管理树数据
     */
    HashMap<String, List<TransferGenWayEntity>> getRecursionTree(boolean showRoot);
}
