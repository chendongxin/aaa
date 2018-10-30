package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerInvalidEntity;

import java.util.List;

/**
 * @author gmm
 */
public interface TransferCustomerInvalidService extends BaseService<TransferCustomerInvalidEntity, Long> {

    /**
     * 设置客户无效
     *
     * @param dto 请求参数
     * @return 返回处理结果
     */
    R setCustomerInvalid(TransferCustomerDTO dto);

    /**
     * 无效客户退回私海
     *
     * @param customerId 客户Id
     * @return 退回结果
     */
    R returnToPrivate(Long customerId);
}
