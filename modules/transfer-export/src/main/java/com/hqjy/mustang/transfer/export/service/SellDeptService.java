package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.SellDeptReportResult;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;

/**
 * @author gmm
 * @date 2018/10/22
 * @apiNote 部门电销排行报表数据服务层
 */
public interface SellDeptService {

    /**
     * 获取部门电销排行报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    SellDeptReportResult sellDeptList(PageParams params, SellQueryParams query);

    /**
     * 导出部门电销排行报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportSellDept(SellQueryParams query);

}
