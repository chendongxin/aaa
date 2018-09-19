package com.hqjy.mustang.allot.service;

import com.hqjy.mustang.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.common.base.base.BaseService;

/**
 * @author : heshuangshuang
 * @date : 2018/6/14 14:33
 */
public interface TransferAllotContactService extends BaseService<TransferAllotCustomerContactEntity, Long> {
    /**
     * 保存联系方式，
     * 按照顺序进行保存并且查重，只要有一个为重单，那么不会保存后续的联系方式，而是直接返回重单对应的初始客户编号
     * 数据库有唯一主键限制，能够保证一种联系方式的号码是唯一的
     */
    ContactSaveResultDTO saveDetail(TransferAllotCustomerEntity content);
}
