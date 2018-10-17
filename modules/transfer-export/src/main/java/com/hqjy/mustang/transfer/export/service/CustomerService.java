package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;

/**
 * @author gmm
 * @date create on 2018/10/10
 * @apiNote
 */
public interface CustomerService {

    /**
     * 导出客户
     *
     * @param query 导出筛选条件
     * @return 返回导出结果
     */
    String exportCustomer(CustomerExportQueryParams query);
}
