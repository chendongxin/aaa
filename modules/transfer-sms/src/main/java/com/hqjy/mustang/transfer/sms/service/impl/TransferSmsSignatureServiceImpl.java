package com.hqjy.mustang.transfer.sms.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsSignatureDao;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsSignatureService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:47
 */
@Service
public class TransferSmsSignatureServiceImpl extends BaseServiceImpl<TransferSmsSignatureDao, TransferSmsSignatureEntity, Long> implements TransferSmsSignatureService {

    /**
     * 根据部门编号获取签名列表
     */
    @Override
    public List<TransferSmsSignatureEntity> getListByDeptId(Long deptId) {
        return baseDao.getListByDeptId(deptId);
    }

    @Override
    public int save(TransferSmsSignatureEntity entity) {
        entity.setCreateUserId(ShiroUtils.getUserId());
        entity.setCreateUserName(ShiroUtils.getUserName());
        return super.save(entity);
    }

    @Override
    public int update(TransferSmsSignatureEntity entity) {
        entity.setUpdateUserId(ShiroUtils.getUserId());
        entity.setUpdateUserName(ShiroUtils.getUserName());
        return super.update(entity);
    }
}
