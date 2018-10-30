package com.hqjy.mustang.transfer.crm.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.transfer.crm.model.dto.TransferCompanySourceDTO;
import com.hqjy.mustang.transfer.crm.model.entity.TransferCompanySourceEntity;

import java.util.List;

/**
 * @author gmm
 */
public interface TransferCompanySourceService extends BaseService<TransferCompanySourceEntity, Long> {


    /**
     * 分页查询推广公司下的推广平台
     *
     * @param pageQuery 分页高级查询参数
     * @return 返回结果集
     */
    List<TransferCompanySourceEntity> findPageSource(PageQuery pageQuery);

    /**
     * 保存推广公司下的推广平台
     *
     * @param transferCompanySourceDTO 保存对象
     * @return 返回保存结果
     */
    int saveCompanySource(TransferCompanySourceDTO transferCompanySourceDTO);
}
