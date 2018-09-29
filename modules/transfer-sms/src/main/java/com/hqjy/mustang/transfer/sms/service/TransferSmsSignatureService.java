package com.hqjy.mustang.transfer.sms.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.sms.model.entity.TransferSmsSignatureEntity;

import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/9/28 14:46
 */
public interface TransferSmsSignatureService extends BaseService<TransferSmsSignatureEntity, Long> {
    /**
     * 根据部门编号获取签名列表
     */
    List<TransferSmsSignatureEntity> getListByDeptId(Long deptId);
}
