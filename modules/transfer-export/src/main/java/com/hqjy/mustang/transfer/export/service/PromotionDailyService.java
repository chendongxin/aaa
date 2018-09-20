package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.PageParams;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转日常报表数据服务层
 */
public interface PromotionDailyService {

    /**
     * 获取招转日常报表数据
     *
     * @param params 分页请求参数
     * @param query  高级请求参数
     * @return 返回查询结果
     */
    R promotionDailyList(PageParams params, DailyQueryParams query);


    /**
     * 导出招转日常报表数据
     *
     * @param query 高级请求参数
     * @return 返回导出结果
     */
    R exportPromotionDaily(DailyQueryParams query);

}
