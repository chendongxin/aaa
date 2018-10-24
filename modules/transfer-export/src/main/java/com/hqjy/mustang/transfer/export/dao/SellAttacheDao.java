package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.DailyQueryParams;
import com.hqjy.mustang.transfer.export.model.query.SellAttacheQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote 电销专员排行报表数据获取层
 */
@Component
@Mapper
public interface SellAttacheDao {

    /**
     * 统计部门上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitBusiness(SellAttacheQueryParams params);

    /**
     * 统计有效商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidBusiness(SellAttacheQueryParams params);

    /**
     * 统计部门成交量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countDealBusiness(SellAttacheQueryParams params);

    /**
     * 统计分配商机总量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countAllotBusiness(SellAttacheQueryParams params);

    /**
     * 统计今日预约上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitTodayAppointBusiness(SellAttacheQueryParams params);

    /**
     * 统计明日预约上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitTomoAppointBusiness(SellAttacheQueryParams params);

    /**
     * 统计部门有效上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitValidBusiness(SellAttacheQueryParams params);
}
