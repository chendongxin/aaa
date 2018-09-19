package com.hqjy.mustang.allot.dao;

import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.allot.model.entity.TransferAllotCustomerContactEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * transfer_customer_contact 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/17 18:10
 */
@Mapper
public interface TransferAllotCustomerContactDao extends BaseDao<TransferAllotCustomerContactEntity, Long> {
    /**
     * 根据联系方式和详情查询一条数据
     */
    TransferAllotCustomerContactEntity findOneByTypeAndDetail(@Param("type") Integer type, @Param("detail") String detail);


    TransferAllotCustomerContactEntity findPrimary(long customer);
}