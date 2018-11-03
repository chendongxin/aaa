package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferProcessEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * transfer_process 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferProcessDao extends BaseDao<TransferProcessEntity, Long> {

    /**
     * 获取当前流程为激活状态的流程数据
     *
     * @param customerId 客户ID
     * @return 返回流程对象
     */
    TransferProcessEntity getProcessByCustomerId(Long customerId);

    /**
     * 获取私海当前流程为激活状态的流程数据
     *
     * @param customerId 客户Id
     * @return 返回结果
     */
    TransferProcessEntity getProcessByCustIdAndUserId(Long customerId);

    /**
     * 修改原来激活状态的流程为过期状态
     *
     * @param entity 流程对象
     * @return 退回结果
     */
    int disableProcessActive(TransferProcessEntity entity);

    /**
     * (批量)获取 商机首次分配给用户流程
     *
     * @param customerIds 客户ID字符串
     * @return 返回结果
     * @author gmm 2018年9月25日19:51:46
     */
    List<TransferProcessEntity> getFirstAllotProcessBatch(@Param("customerIds") String customerIds);

    /**
     * 查询当天用户拥有商机数量
     *
     * @param map 查询参数
     * @return 返回数量
     * @author gmm 2018-9-28 17:28:24
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
     * 根据用户id获取所有非成交，有效状态的商机流程,主要用于删除人员和部门变更放入公海
     *
     * @author HSS 2018-08-11
     */
    List<TransferProcessEntity> findUserActive(@Param("userId") Long userId, @Param("sign") Boolean sign);

    /**
     * 修改原来激活状态的流程为过期状态(批量)
     *
     * @param processIdList 流程id
     * @return 退回结果
     */
    int disableProcessActiveBatch(@Param("processIdList") String processIdList);
}