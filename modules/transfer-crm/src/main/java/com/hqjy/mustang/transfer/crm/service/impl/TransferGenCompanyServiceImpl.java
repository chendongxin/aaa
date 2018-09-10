package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferGenCompanyDao;
import com.hqjy.mustang.transfer.crm.entity.TransferGenCompanyEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenCompanyService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TransferGenCompanyServiceImpl extends BaseServiceImpl<TransferGenCompanyDao, TransferGenCompanyEntity, Long> implements TransferGenCompanyService {

    @Override
    public List<TransferGenCompanyEntity> getAllGenCompanyList() {
        return baseDao.getAllGenCompanyList();
    }

    @Override
    public HashMap<String, List<TransferGenCompanyEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferGenCompanyEntity.class, "getCompanyId", getAllGenCompanyList(), Collections.singletonList(SystemId.TREE_ROOT));
    }


}
