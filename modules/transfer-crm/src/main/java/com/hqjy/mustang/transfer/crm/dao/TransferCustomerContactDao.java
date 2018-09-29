package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerContactEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * transfer_customer_contact 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerContactDao extends BaseDao<TransferCustomerContactEntity, Integer> {

    /**
     * 根据联系方式和详情，查询具体信息
     *
     * @author HSS 2018-06-25
     */
    TransferCustomerContactEntity findOneByDetail(@Param("type") Integer type, @Param("detail") String detail);

    /**
     * (批量)获取客户的所有联系方式
     *
     * @param customerIds 客户ID
     * @return 客户的所有联系方式
     * @author XYQ 2018年8月20日10:25:46
     */
    List<TransferCustomerContactEntity> findListByCustomerIdBatch(@Param("customerIds") String customerIds);

    /**
     * 获取该客户的所有联系方式
     *
     * @param customerId 客户ID
     * @return 客户的所有联系方式
     */
    List<TransferCustomerContactEntity> findListByCustomerId(Long customerId);
}