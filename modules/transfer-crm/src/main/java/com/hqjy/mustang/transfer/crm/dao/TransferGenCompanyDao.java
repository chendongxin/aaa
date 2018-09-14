package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferGenCompanyEntity;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_gen_company 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/07 10:16
 */
@Mapper
public interface TransferGenCompanyDao extends BaseDao<TransferGenCompanyEntity, Long> {

    /**
     * 获取所有推广公司列表
     */
    List<TransferGenCompanyEntity> getAllGenCompanyList();

    /**
     * 通过名称查询推广公司
     */
    TransferGenCompanyEntity findOneByName(String name);
}