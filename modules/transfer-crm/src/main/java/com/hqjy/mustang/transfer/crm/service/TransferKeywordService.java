package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;

import java.util.HashMap;
import java.util.List;

public interface TransferKeywordService extends BaseService<TransferKeywordEntity, Integer> {

    /**
     * 获取所有关键词列表
     */
    List<TransferKeywordEntity> getAllKeywordList();

    /**
     * 关键词管理树数据
     */
    HashMap<String, List<TransferKeywordEntity>> getRecursionTree(boolean showRoot);
}
