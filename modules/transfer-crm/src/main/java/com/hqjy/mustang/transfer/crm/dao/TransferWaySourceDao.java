package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferWaySourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * transfer_way_source 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/17 12:04
 */
@Component
@Mapper
public interface TransferWaySourceDao extends BaseDao<TransferWaySourceEntity, Long> {

    /**
     * 查询平台下的推广方式
     */
    List<TransferWaySourceEntity> findBySourceId(Long sourceId);

    /**
     * 获取来源平台下的推广方式列表
     *
     */
    List<TransferWaySourceEntity> listPageSource(PageQuery pageQuery);

    /**
     * 根据wayId和sourceId查询是否存在重复数据
     */
    TransferWaySourceEntity findByWayIdAndSourceId(@Param("wayId") Long wayId, @Param("sourceId") Long sourceId);

}