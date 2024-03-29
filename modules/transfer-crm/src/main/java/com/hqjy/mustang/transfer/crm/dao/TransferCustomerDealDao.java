package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCustomerDealEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * transfer_customer_deal 持久化层
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferCustomerDealDao extends BaseDao<TransferCustomerDealEntity, Long> {

    /**
     * 更新成交状态
     *
     * @param dealEntity 成交对象
     * @return 返回影响行数
     * @author xyq
     * @date 2018年10月8日11:59:51
     */
    int updateIsDelete(TransferCustomerDealEntity dealEntity);
}