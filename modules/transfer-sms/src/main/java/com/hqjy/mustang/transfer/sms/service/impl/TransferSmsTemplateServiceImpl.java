package com.hqjy.mustang.transfer.sms.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import com.hqjy.mustang.transfer.sms.dao.TransferSmsTemplateDao;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsTemplateEntity;
import com.hqjy.mustang.transfer.sms.service.TransferSmsTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:47
 */
@Service
public class TransferSmsTemplateServiceImpl extends BaseServiceImpl<TransferSmsTemplateDao, TransferSmsTemplateEntity, Long> implements TransferSmsTemplateService {
    /**
     * 根据部门编号获取模版列表
     */
    @Override
    public List<TransferSmsSignatureEntity> getListByDeptId(Long deptId) {
        return baseDao.getListByDeptId(deptId);
    }

    @Override
    public int save(TransferSmsTemplateEntity entity) {
        entity.setCreateUserId(ShiroUtils.getUserId());
        entity.setCreateUserName(ShiroUtils.getUserName());
        return super.save(entity);
    }

    @Override
    public int update(TransferSmsTemplateEntity entity) {
        entity.setUpdateUserId(ShiroUtils.getUserId());
        entity.setUpdateUserName(ShiroUtils.getUserName());
        return super.update(entity);
    }
}
