package com.hqjy.mustang.allot.dao;

import com.hqjy.mustang.allot.model.entity.TransferAllotProcessEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * transfer_process 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/17 17:13
 */
@Mapper
public interface TransferAllotProcessDao extends BaseDao<TransferAllotProcessEntity, Long> {

    /**
     * 客户编号查找一条当前激活状态流程记录
     */
    TransferAllotProcessEntity findOneActiveByCustomerId(Long customerId);

    /**
     * 保存一条激活状态流程记录
     */
    int saveActive(TransferAllotProcessEntity allotProcessEntity);

    /**
     * 更新客户所有流程记录为未激活状态
     */
    int updateNotActivated(@Param("customerId") Long customerId);

    /**
     * 更新指定流程记录为激活状态
     */
    int updateActive(@Param("processId") Long processId);
}