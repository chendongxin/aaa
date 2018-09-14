package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferGenWayDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenWayServiceImpl extends BaseServiceImpl<TransferGenWayDao, TransferGenWayEntity, Long> implements TransferGenWayService {

    /**
     * 获取所有推广方式
     */
    @Override
    public List<TransferGenWayEntity> getAllGenWayList() {
        return baseDao.getAllGenWayList();
    }


    /**
     * 推广方式管理树数据
     */
    @Override
    public HashMap<String, List<TransferGenWayEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferGenWayEntity.class, "getWayId", getAllGenWayList(), Collections.singletonList(SystemId.TREE_ROOT));
    }

    /**
     * 增加推广方式
     */
    @Override
    public int save(TransferGenWayEntity transferGenWayEntity) {
        if (baseDao.findOneByGenName(transferGenWayEntity.getGenWay()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferGenWayEntity.setCreateUserId(getUserId());
        transferGenWayEntity.setCreateUserName(getUserName());
        return baseDao.save(transferGenWayEntity);
    }

    /**
     * 删除推广方式
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] wayIds) {
        List<Long> list = Arrays.asList(wayIds);
        for (Long wayId : list) {
            List<TransferGenWayEntity> genWayList = baseDao.findByParentId(wayId);
            if (genWayList.size() > 0) {
                throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
            }
        }
        return super.deleteBatch(wayIds);
    }

    /**
     * 修改推广方式
     */
    @Override
    public int update(TransferGenWayEntity transferGenWayEntity) {
        transferGenWayEntity.setUpdateUserId(getUserId());
        transferGenWayEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferGenWayEntity);
    }

}
