package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转日常数据获取层
 */
@Component
@Mapper
public interface PromotionDailyDao {
    /**
     * 统计部门商机总量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countCreateBusiness(DailyQueryParams params);

    /**
     * 统计部门已跟进商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countFollowBusiness(DailyQueryParams params);


    /**
     * 统计部门有效商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidBusiness(DailyQueryParams params);


    /**
     * 统计部门非失败商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countNoInvalidBusiness(DailyQueryParams params);


    /**
     * 统计部门商机上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitBusiness(DailyQueryParams params);


    /**
     * 统计部门有效上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidVisitBusiness(DailyQueryParams params);

    /**
     * 统计部门意向量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countIntentionBusiness(DailyQueryParams params);

    /**
     * 统计部门成交量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countDealBusiness(DailyQueryParams params);

}
