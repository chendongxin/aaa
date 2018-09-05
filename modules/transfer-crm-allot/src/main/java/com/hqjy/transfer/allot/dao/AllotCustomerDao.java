package com.hqjy.transfer.allot.dao;

import com.hqjy.transfer.allot.model.entity.AllotCustomerEntity;
import com.hqjy.transfer.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * biz_customer 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/14 09:44
 */
@Mapper
public interface AllotCustomerDao extends BaseDao<AllotCustomerEntity, Long> {
    /**
     * 修改customer的ID,主要修改新增加的用户的ID为老客户的ID,因为老客户ID不存在在，但是联系表中还存在
     */
    int updateCustomerId(@Param("customerId") Long customerId, @Param("newId") Long newId);

    /**
     * 更新联系次数
     */
    int updateConsult(Long customerId);

    /**
     * 分配成功，设置部门ID和人员ID
     */
    int updateProcessInfo(AllotCustomerEntity customerEntity);
}