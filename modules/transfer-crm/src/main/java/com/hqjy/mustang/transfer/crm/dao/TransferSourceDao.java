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
     * @param companyId 推广公司名称
     * @return 返回结果
     */
    List<TransferSourceEntity> findNotByCompanyId(Long companyId);

    /**
     * 查询在该公司下的推广平台
     * @param companyId 推广公司名称
     * @return 返回结果
     */
    List<TransferSourceEntity> findByCompanyId(Long companyId);

    /**
     * 获取所有来源平台列表
     * @param
     * @return 返回结果
     */
    List<TransferSourceEntity> getAllSourceList();

    /**
     * 根据邮箱后缀查询来源
     * @param emailDomain 邮件详情
     * @return 返回结果
     */
    TransferSourceEntity findByEmailDomain(String emailDomain);
}