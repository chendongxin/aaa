package com.hqjy.mustang.transfer.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferCompanySourceDao;
import com.hqjy.mustang.transfer.crm.dao.TransferGenCompanyDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCompanyEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferGenCompanyServiceImpl extends BaseServiceImpl<TransferGenCompanyDao, TransferGenCompanyEntity, Long> implements TransferGenCompanyService {

    @Autowired
    private TransferCompanySourceDao transferCompanySourceDao;


    @Override
    public List<TransferCompanySourceEntity> findPageSource(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return transferCompanySourceDao.findPage(pageQuery);
    }

    /**
     * 获取所有推广公司
     */
    @Override
    public List<TransferGenCompanyEntity> getAllGenCompanyList() {
        return baseDao.getAllGenCompanyList();
    }

    /**
     * 推广公司管理树数据
     */
    @Override
    public HashMap<String, List<TransferGenCompanyEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferGenCompanyEntity.class, "getCompanyId", getAllGenCompanyList(), Collections.singletonList(SystemId.TREE_ROOT));
    }

    /**
     * 增加一个推广公司
     */
    @Override
    public int save(TransferGenCompanyEntity transferGenCompanyEntity) {
        if (baseDao.findOneByName(transferGenCompanyEntity.getName()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferGenCompanyEntity.setParentId(1L);
        transferGenCompanyEntity.setCreateUserId(getUserId());
        transferGenCompanyEntity.setCreateUserName(getUserName());
        return baseDao.save(transferGenCompanyEntity);
    }

    /**
     * 修改推广公司
     */
    @Override
    public int update(TransferGenCompanyEntity transferGenCompanyEntity) {
        transferGenCompanyEntity.setUpdateUserId(getUserId());
        transferGenCompanyEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferGenCompanyEntity);
    }

    /**
     * 删除推广公司
     * @param companyIds 删除推广公司数组
     * @return int>0 为删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] companyIds) {
        List<Long> list = Arrays.asList(companyIds);
        for (Long companyId : list) {
            List<TransferCompanySourceEntity> companySourceList = transferCompanySourceDao.findByCompanyId(companyId);
            if (companySourceList.size() > 0) {
                throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
            }
        }
        return super.deleteBatch(companyIds);
    }


    /**
     * 保存推广公司下的推广平台
     */
    @Override
    public int saveCompanySource(TransferCompanySourceEntity transferCompanySourceEntity) {
        TransferCompanySourceEntity newCompanySourceEntity = PojoConvertUtil.convert(transferCompanySourceEntity, TransferCompanySourceEntity.class);
        newCompanySourceEntity.setCreateUserId(getUserId());
        newCompanySourceEntity.setCreateUserName(getUserName());
        return transferCompanySourceDao.save(newCompanySourceEntity);
    }



}
