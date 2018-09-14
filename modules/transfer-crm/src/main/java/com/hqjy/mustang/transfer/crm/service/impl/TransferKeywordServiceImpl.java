package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferKeywordDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferKeywordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferKeywordServiceImpl extends BaseServiceImpl<TransferKeywordDao, TransferKeywordEntity, Integer> implements TransferKeywordService {

    /**
     * 获取所有关键词
     */
    @Override
    public List<TransferKeywordEntity> getAllKeywordList() {
        return baseDao.getAllKeywordList();
    }

    /**
     * 关键词管理树数据
     */
    @Override
    public HashMap<String, List<TransferKeywordEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferKeywordEntity.class, "getId", getAllKeywordList(), Collections.singletonList(SystemId.TREE_ROOT_INTE));
    }

    /**
     * 增加一条关键词
     */
    @Override
    public int save(TransferKeywordEntity transferKeywordEntity) {
        if (baseDao.findOneByName(transferKeywordEntity.getName()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferKeywordEntity.setCreateUserId(getUserId());
        transferKeywordEntity.setCreateUserName(getUserName());
        return baseDao.save(transferKeywordEntity);
    }

    /**
     * 修改一条关键词
     */
    @Override
    public int update(TransferKeywordEntity transferKeywordEntity) {
        transferKeywordEntity.setUpdateUserId(getUserId());
        transferKeywordEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferKeywordEntity);
    }

    /**
     * 删除关键词
     * @param ids 删除关键词数组
     * @return int>0 为删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);
        for (Integer id : list) {
            List<TransferKeywordEntity> keywordList = baseDao.findByParentId(id);
            if (keywordList.size() > 0) {
                throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
            }
        }
        return super.deleteBatch(ids);
    }

}
