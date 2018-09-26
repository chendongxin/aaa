package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerTransferDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerUpDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import org.springframework.web.multipart.MultipartFile;

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
     * 导入客户
     *
     * @param file 导入的文件
     * @param dto  请求输入参数
     * @return 返回导入结果
     */
    R importCustomer(MultipartFile file, TransferCustomerUpDTO dto);

    /**
     * 导出客户
     *
     * @param query 导出帅选条件
     * @return 返回导出结果
     */
    R exportCustomer(PageQuery query);

}
