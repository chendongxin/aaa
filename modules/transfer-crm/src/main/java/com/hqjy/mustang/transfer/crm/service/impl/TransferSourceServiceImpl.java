package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferSourceDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenWayEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferGenWayService;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferSourceServiceImpl extends BaseServiceImpl<TransferSourceDao, TransferSourceEntity, Long> implements TransferSourceService {

    @Autowired
    private TransferGenWayService transferGenWayService;

    /**
     * 获取所有推广方式
     */
    @Override
    public List<TransferSourceEntity> getAllSourceList() {
        return baseDao.getAllSourceList();
    }


    /**
     * 推广方式管理树数据
     */
    @Override
    public HashMap<String, List<TransferSourceEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferSourceEntity.class, "getSourceId", getAllSourceList(), Collections.singletonList(SystemId.TREE_ROOT));
    }

    /**
     * 分页查询
     */
    @Override
    public List<TransferSourceEntity> findPage(PageQuery pageQuery) {
        List<TransferSourceEntity> list = super.findPage(pageQuery);
        return list;
    }

    /**
     * 增加一条来源
     */
    @Override
    public int save(TransferSourceEntity transferSourceEntity) {
        if (baseDao.findOneByName(transferSourceEntity.getName()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        return baseDao.save(transferSourceEntity.setSign(0).setCreateUserId(getUserId()).setCreateUserName(getUserName()));
    }

    /**
     * 修改一条来源
     */
    @Override
    public int update(TransferSourceEntity transferSourceEntity) {
        transferSourceEntity.setUpdateUserId(getUserId());
        transferSourceEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferSourceEntity);
    }

    /**
     * 获取不属于指定公司的推广平台
     */
    @Override
    public List<TransferSourceEntity> findNotByCompanyId(Long companyId) {
        return baseDao.findNotByCompanyId(companyId);
    }

    /**
     * 获取属于指定公司的推广平台
     */
    @Override
    public List<TransferSourceEntity> findByCompanyId(Long companyId) {
        return baseDao.findByCompanyId(companyId);
    }

    /**
     * 删除所选来源平台
     */
    @Override
    public int delete(Long sourceId) {
        List<TransferGenWayEntity> genWay = transferGenWayService.findBySourceId(sourceId);
        if (genWay.size() > 0) {
            throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
        }
        if (baseDao.findOne(sourceId).getSign() == 1) {
            throw new RRException(StatusCode.DATABASE_SELECT_USE);
        }
        return super.delete(sourceId);
    }




    /**
     * 根据邮箱后缀查询来源
     */
    @Override
    public TransferSourceEntity findByEmailDomain(String emailDomain) {
        return baseDao.findByEmailDomain(emailDomain);
    }
}
