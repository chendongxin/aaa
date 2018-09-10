package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TransferGenWayServiceImpl extends BaseServiceImpl<TransferGenWayDao, TransferGenWayEntity, Long> implements TransferGenWayService {

    @Override
    public List<TransferGenWayEntity> getAllGenWayList() {
        return baseDao.getAllGenWayList();
    }

    @Override
    public HashMap<String, List<TransferGenWayEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferGenWayEntity.class, "getCompanyId", getAllGenWayList(), Collections.singletonList(SystemId.TREE_ROOT));
    }

}
