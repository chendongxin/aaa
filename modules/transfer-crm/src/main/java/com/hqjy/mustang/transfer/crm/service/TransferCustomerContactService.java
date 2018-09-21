package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;

public interface TransferCustomerContactService extends BaseService<TransferCustomerContactEntity, Integer> {

    /**
     * 根据联系方式和详情，查询具体信息
     *
     * @author HSS 2018-06-25
     */
    TransferCustomerContactEntity getByDetail(Integer type, String detail);

    /**
     * 新增客户联系方式
     *
     * @param dto dto
     * @return i
     */
    int save(TransferCustomerDTO dto);
}
