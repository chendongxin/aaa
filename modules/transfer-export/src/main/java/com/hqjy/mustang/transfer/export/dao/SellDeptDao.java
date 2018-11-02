package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote 部门电销排行报表数据获取层
 */
@Component
@Mapper
public interface SellDeptDao {

    /**
     * 统计部门商机总量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countBusiness(SellQueryParams params);

    /**
     * 统计部门上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitBusiness(SellQueryParams params);


    /**
     * 统计有效商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidBusiness(SellQueryParams params);

    /**
     * 统计部门成交量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countDealBusiness(SellQueryParams params);


    /**
     * 统计电销部门预约量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countReservation(SellQueryParams params);

    /**
     * 统计部门有效上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitValidBusiness(SellQueryParams params);
}
