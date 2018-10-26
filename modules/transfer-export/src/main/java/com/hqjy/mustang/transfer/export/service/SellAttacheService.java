package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.SellAttacheReportResult;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;

/**
 * @author gmm
 * @date 2018/10/18 11:44
 * @apiNote 电销专员排行报表数据服务层
 */
public interface SellAttacheService {

    /**
     * 获取电销专员排行报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    SellAttacheReportResult sellAttacheList(PageParams params, SellQueryParams query);

    /**
     * 导出电销专员排行报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportSellAttache(SellQueryParams query);
}
