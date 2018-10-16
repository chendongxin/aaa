package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.query.InvalidQueryParams;

public interface InvalidService {


    /**
     * 导出无效客户
     *
     * @param query 导出筛选条件
     * @return 返回导出结果
     */
    String exportInvalid(InvalidQueryParams query);

}
