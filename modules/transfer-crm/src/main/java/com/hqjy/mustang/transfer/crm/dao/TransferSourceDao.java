package com.hqjy.mustang.transfer.crm.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferSourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * transfer_source 持久化层
 *
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
public interface TransferSourceDao extends BaseDao<TransferSourceEntity, Long> {

    /**
     * 通过名称查询一条来源平台
     *
     * @param name 来源名称
     * @return 返回结果
     */
    TransferSourceEntity findOneByName(String name);

    /**
     * 查询不在该公司下的推广平台
     */
    List<TransferSourceEntity> findNotByCompanyId(Long companyId);

    /**
     * 获取所有推广方式列表
     */
    List<TransferSourceEntity> getAllSourceList();

    /**
     * 根据邮箱后缀查询来源
     */
    TransferSourceEntity findByEmailDomain(String emailDomain);
}