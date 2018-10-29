package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;

import java.util.List;

/**
 * @author gmm
 */
public interface TransferCustomerContactService extends BaseService<TransferCustomerContactEntity, Integer> {


    /**
     * 根据联系方式和详情，查询具体信息
     *
     * @param type   联系方式
     * @param detail 详情
     * @return 返回结果
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

    /**
     * 根据联系方式精确获取客户Id,供客户管理和客服管理分页查询条件刷选
     *
     * @param pageQuery 分页参数对象
     * @author gmm 2018年9月25日14:45:09
     */
    void setCustomerIdByContact(PageQuery pageQuery);


    /**
     * 获取该客户的所有联系方式
     *
     * @param customerId 客户ID
     * @return 客户的所有联系方式
     */
    List<TransferCustomerContactEntity> findListByCustomerId(Long customerId);
}
