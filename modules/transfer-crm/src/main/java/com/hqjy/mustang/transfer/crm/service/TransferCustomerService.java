package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerDetailDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerTransferDTO;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCustomerUpDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransferCustomerService extends BaseService<TransferCustomerEntity, Long> {

    /**
     * 获取某客户的基本数据
     *
     * @param customerId 客户编号
     * @return 返回结果
     */
    TransferCustomerDetailDTO getCustomerData(Long customerId);

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
     * 公海客户数据
     *
     * @param pageQuery 查询参数对象
     * @return 公海客户数据
     */
    List<TransferCustomerEntity> findCommonPage(PageQuery pageQuery);

    /**
     * 公海领取客户到私海
     *
     * @param customerId 客户Id
     * @return 领取结果
     */
    R receiveTransferCustomer(List<Long> customerId);

    /**
     * 格式化 查询时间
     *
     * @param pageQuery 查询参数对象
     */
    void formatQueryTime(PageQuery pageQuery);

    /**
     * 私海客户数据
     *
     * @param pageQuery 查询参数对象
     * @return 私海客户数据
     */
    List<TransferCustomerEntity> findPrivatePage(PageQuery pageQuery);

    /**
     * 私海客户退回公海
     *
     * @param customerId 客户Id
     * @return 退回结果
     */
    R returnToCommon(List<Long> customerId);

    /**
     * 根据电话和部门，查询一个客户信息
     */
    TransferCustomerEntity findByPhoneAndDeptId(Long deptId, String phone);

    /**
     * 根据NCid查询客户信息
     *
     * @param ncId NCid
     * @return 客户信息
     * @author xieyuqing
     * @date create 2018年10月8日10:20:06
     */
    TransferCustomerEntity getByNcId(String ncId);

    /**
     * 导入客户
     *
     * @param file 导入的文件
     * @param dto  请求输入参数
     * @return 返回导入结果
     */
    R importCustomer(MultipartFile file, TransferCustomerUpDTO dto);

}
