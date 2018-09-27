package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerContactDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;

import java.util.List;

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

    /**
     * 根据联系方式精确获取客户Id,供客户管理和客服管理分页查询条件刷选
     *
     * @param pageQuery 分页参数对象
     * @return 返回处理后的参数对象
     * @author gmm 2018年9月25日14:45:09
     */
    PageQuery setCustomerIdByContact(PageQuery pageQuery);

    /**
     * (批量)获取客户的所有联系方式
     *
     * @param customerIds 客户ID
     * @return 客户的所有联系方式
     * @author XYQ 2018年8月20日10:25:46
     */
    List<TransferCustomerContactEntity> findListByCustomerIdBatch(String customerIds);

    /**
     * (批量)获取客户的联系方式
     *
     * @param customerIds 客户编号
     * @return 返回结果
     * @author XYQ 2018年8月20日10:25:46
     */
    List<TransferCustomerContactDTO> findByCustomerIds(String customerIds);

    /**
     * 获取该客户的所有联系方式
     *
     * @param customerId 客户ID
     * @return 客户的所有联系方式
     */
    List<TransferCustomerContactEntity> findListByCustomerId(Long customerId);
}
