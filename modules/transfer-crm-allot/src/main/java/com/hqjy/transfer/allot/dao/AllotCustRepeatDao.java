package com.hqjy.transfer.allot.dao;

import com.hqjy.transfer.allot.model.entity.AllotCustRepeatEntity;
import com.hqjy.transfer.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * biz_customer_repeat 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/19 10:32
 */
@Mapper
public interface AllotCustRepeatDao extends BaseDao<AllotCustRepeatEntity, Long> {
}