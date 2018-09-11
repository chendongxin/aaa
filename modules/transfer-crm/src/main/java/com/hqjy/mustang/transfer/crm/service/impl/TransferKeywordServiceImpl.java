package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferKeywordDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferKeywordService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TransferKeywordServiceImpl extends BaseServiceImpl<TransferKeywordDao, TransferKeywordEntity, Integer> implements TransferKeywordService {

    @Override
    public List<TransferKeywordEntity> getAllKeywordList() {
        return baseDao.getAllKeywordList();
    }

    @Override
    public HashMap<String, List<TransferKeywordEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferKeywordEntity.class, "getCompanyId", getAllKeywordList(), Collections.singletonList(SystemId.TREE_ROOT));
    }
}
