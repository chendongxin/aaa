package com.hqjy.transfer.allot.dao;

import com.hqjy.transfer.allot.model.entity.AllotContactEntity;
import com.hqjy.transfer.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * biz_customer_contact 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/13 15:41
 */
@Mapper
public interface AllotContactDao extends BaseDao<AllotContactEntity, Long> {

    /**
     * 根据联系方式和详情查询一条数据
     */
    AllotContactEntity findOneByTypeAndDetail(@Param("type") Integer type, @Param("detail") String detail);

    /**
     * 查询主联系方式
     */
    AllotContactEntity findPrimary(@Param("customerId") long customerId);
}