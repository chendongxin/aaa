package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;

import java.util.List;
import java.util.Map;
/**
 * @author gmm
 */
public interface TransferProcessService extends BaseService<TransferProcessEntity, Long> {

    /**
     * 获取当前流程为激活状态的流程数据
     *
     * @param customerId 客户ID
     * @return 返回流程对象
     */
    TransferProcessEntity getProcessByCustomerId(Long customerId);

    /**
     * 设置客户流程过期
     *
     * @param entity 流程对象
     * @return 退回结果
     */
    int disableProcessActive(TransferProcessEntity entity);

    /**
     * (批量)获取 商机首次分配给用户流程
     * @param customerIds 客户ID字符串
     * @return 返回结果
     * @author gmm 2018年9月25日19:51:46
     */
    List<TransferProcessEntity> getFirstAllotProcessBatch(String customerIds);

    /**
     * 获取私海当前用户Id和客户Id获取其对应流程数据
     *
     * @param customerId 客户Id
     * @return 返回结果
     */
    TransferProcessEntity getProcessByCustIdAndUserId(Long customerId);

    /**
     * 查询当天用户拥有商机数量
     *
     * @param map 查询参数
     * @return 返回数量
     * @author xyq 2018-7-18 17:28:24
     */
    int countHasTotal(Map<String, Object> map);

    /**
     * 获取公海当前流程为激活状态的流程数据
     *
     * @param customerId 客户ID
     * @return 返回流程对象
     */
    TransferProcessEntity getProcessByPublicCustomerId(Long customerId);

    /**
     * 根据用户id更新用户非成交商机到公海
     *
     * @author HSS 2018-08-11
     */
    int updateUserTransferProcessToPublic(Long userId, boolean sign);
}
