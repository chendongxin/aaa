package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;

import java.util.List;

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
}
