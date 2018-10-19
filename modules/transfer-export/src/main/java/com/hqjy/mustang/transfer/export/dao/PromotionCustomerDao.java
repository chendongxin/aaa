package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.CustomerQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 客服推广报表数据获取层
 */
@Component
@Mapper
public interface PromotionCustomerDao {

    /**
     * 统计部门商机总量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countCreateBusiness(CustomerQueryParams params);


    /**
     * 统计部门有效商机量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidBusiness(CustomerQueryParams params);


    /**
     * 统计部门商机上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countVisitBusiness(CustomerQueryParams params);


    /**
     * 统计部门有效上门量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countValidVisitBusiness(CustomerQueryParams params);


    /**
     * 统计部门成交量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countDealBusiness(CustomerQueryParams params);
}
