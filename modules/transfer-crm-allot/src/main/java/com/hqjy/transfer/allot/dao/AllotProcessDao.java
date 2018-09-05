package com.hqjy.transfer.allot.dao;

import com.hqjy.transfer.allot.model.entity.AllotProcessEntity;
import com.hqjy.transfer.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * biz_process 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/06/15 14:45
 */
@Mapper
public interface AllotProcessDao extends BaseDao<AllotProcessEntity, Long> {

    /**
     * 客户编号查找一条当前激活状态流程记录
     */
    AllotProcessEntity findOneActiveByCustomerId(Long customerId);

    /**
     * 保存一条激活状态流程记录
     */
    int saveActive(AllotProcessEntity allotProcessEntity);

    /**
     * 更新客户所有流程记录为未激活状态
     */
    int updateNotActivated(@Param("customerId") Long customerId);

    /**
     * 更新指定流程记录为激活状态
     */
    int updateActive(@Param("processId") Long processId);

}