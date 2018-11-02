package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * transfer_company_source 持久化层
 *
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Component
@Mapper
public interface TransferCompanySourceDao extends BaseDao<TransferCompanySourceEntity, Long> {

    /**
     * 查询公司下的平台
     */
    List<TransferCompanySourceEntity> findByCompanyId(Long companyId);


    /**
     * 获取指定公司下的来源平台列表
     */
    List<TransferCompanySourceEntity> listPageSource(PageQuery pageQuery);

    /**
     * 根据companyId和sourceId查找一条记录
     */
    TransferCompanySourceEntity findByCompanyIdAndSourceId(@Param("companyId") Long companyId, @Param("sourceId") Long sourceId);

}