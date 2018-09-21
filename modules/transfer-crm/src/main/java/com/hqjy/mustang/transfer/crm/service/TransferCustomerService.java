package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerTransferDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;

public interface TransferCustomerService extends BaseService<TransferCustomerEntity, Long> {

    /**
     * 获取某客户的基本数据
     *
     * @param customerId 客户编号
     * @return 返回结果
     */
    TransferCustomerEntity getCustomerData(Long customerId);

    /**
     * 新增客户
     *
     * @param customerDto 客户信息对象
     */
    R saveTransferCustomer(TransferCustomerDTO customerDto);

    /**
     * 转移客户
     *
     * @param dto 请求参数
     * @return 返回转移结果
     */
    R transferCustomer(TransferCustomerTransferDTO dto);

    /**
     * 更新客户基本信息：此方法只更新customerDetail中的信息
     *
     * @param dto 客户信息
     * @author gmm 2018年9月21日16:23:13
     */
    void updateBaseData(TransferCustomerDTO dto);
}
