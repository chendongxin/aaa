package com.hqjy.mustang.transfer.sms.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsTemplateEntity;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:46
 */
public interface TransferSmsTemplateService extends BaseService<TransferSmsTemplateEntity, Long> {
    /**
     * 根据部门编号获取模版列表
     */
    List<TransferSmsSignatureEntity> getListByDeptId(Long deptId);
}
