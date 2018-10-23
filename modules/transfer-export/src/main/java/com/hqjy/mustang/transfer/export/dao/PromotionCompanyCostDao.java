package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CompanyCostEntity;
import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.CompanyCostQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 推广公司费用报表数据获取层
 */
@Mapper
@Component
public interface PromotionCompanyCostDao {

    /**
     * 获取推广公司费用报表数据
     *
     * @param params 条件
     * @return 返回结果集
     */
    List<CompanyCostEntity> getCompanyCost(CompanyCostQueryParams params);

    /**
     * 获取总商机
     *
     * @param params 条件
     * @return 返回结果集
     */
    List<CustomerEntity> countCustomer(CompanyCostQueryParams params);
}
