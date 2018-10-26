package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.dto.SellCallReportResult;
import com.hqjy.mustang.transfer.export.model.query.PageParams;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;

/**
 * @author gmm
 * @date 2018/10/22
 * @apiNote 电销商机拨打报表数据服务层
 */
public interface SellCallService {

    /**
     * 获取电销商机拨打排行报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    SellCallReportResult sellCallList(PageParams params, SellQueryParams query);

    /**
     * 导出电销商机拨打排行报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    String exportSellCall(SellQueryParams query);
}
