package com.hqjy.mustang.allot.dao;

import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * transfer_customer 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferAllotCustomerDao extends BaseDao<TransferAllotCustomerEntity, Long> {
    /**
     * 修改customer的ID,主要修改新增加的用户的ID为老客户的ID,因为老客户ID不存在在，但是联系表中还存在
     */
    int updateCustomerId(@Param("customerId") Long customerId, @Param("newId") Long newId);

    /**
     * 分配成功，设置部门ID和人员ID
     */
    int updateProcessInfo(TransferAllotCustomerEntity customerEntity);
}