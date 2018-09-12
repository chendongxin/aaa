package com.hqjy.mustang.transfer.crm.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.dao.TransferSourceDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import com.hqjy.mustang.transfer.crm.service.TransferSourceService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
public class TransferSourceServiceImpl extends BaseServiceImpl<TransferSourceDao, TransferSourceEntity, Long> implements TransferSourceService {

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
    public int save(TransferSourceEntity transferSourceEntity) {
        if (baseDao.findOneByName(transferSourceEntity.getName()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferSourceEntity.setCreateUserId(getUserId());
        transferSourceEntity.setCreateUserName(getUserName());
        return baseDao.save(transferSourceEntity);
    }

    /**
     * 修改一条来源
     */
    public int update(TransferSourceEntity transferSourceEntity) {
        transferSourceEntity.setUpdateUserId(getUserId());
        transferSourceEntity.setUpdateUserName(getUserName());
        return baseDao.update(transferSourceEntity);
    }

}
