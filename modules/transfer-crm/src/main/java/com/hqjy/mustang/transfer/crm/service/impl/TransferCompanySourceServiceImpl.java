package com.hqjy.mustang.transfer.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.PojoConvertUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferCompanySourceDao;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCompanySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferCompanySourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

/**
 * @author gmm
 */
@Service
public class TransferCompanySourceServiceImpl extends BaseServiceImpl<TransferCompanySourceDao, TransferCompanySourceEntity, Long> implements TransferCompanySourceService {

    private TransferCompanySourceDao transferCompanySourceDao;

    @Autowired
    public void setTransferCompanySourceDao(TransferCompanySourceDao transferCompanySourceDao) {
        this.transferCompanySourceDao = transferCompanySourceDao;
    }

    @Override
    public List<TransferCompanySourceEntity> findPageSource(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        return transferCompanySourceDao.listPageSource(pageQuery);
    }


    @Override
    public int saveCompanySource(TransferCompanySourceDTO companySource) {
        List<TransferCompanySourceEntity> companySourceList = transferCompanySourceDao.findByCompanyId(companySource.getCompanyId());
        for (TransferCompanySourceEntity transferCompanySource : companySourceList) {
            if (transferCompanySource.getSourceId().equals(companySource.getSourceId())) {
                throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
            }
        }
        TransferCompanySourceEntity newCompanySourceEntity = PojoConvertUtil.convert(companySource, TransferCompanySourceEntity.class);
        newCompanySourceEntity.setCreateUserId(getUserId());
        newCompanySourceEntity.setCreateUserName(getUserName());
        return transferCompanySourceDao.save(newCompanySourceEntity);
    }

}
