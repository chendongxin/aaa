package com.hqjy.transfer.allot.service;

import com.hqjy.transfer.allot.model.dto.ContactSaveResultDTO;
import com.hqjy.transfer.allot.model.entity.AllotContactEntity;
import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;
import com.hqjy.transfer.common.base.base.BaseService;

/**
 * @author : heshuangshuang
 * @date : 2018/6/14 14:33
 */
public interface AllotContactService extends BaseService<AllotContactEntity, Long> {
    /**
     * 保存联系方式，
     * 按照顺序进行保存并且查重，只要有一个为重单，那么不会保存后续的联系方式，而是直接返回重单对应的初始客户编号
     * 数据库有唯一主键限制，能够保证一种联系方式的号码是唯一的
     */
    ContactSaveResultDTO saveDetail(AllotCustomerEntity content);
}
