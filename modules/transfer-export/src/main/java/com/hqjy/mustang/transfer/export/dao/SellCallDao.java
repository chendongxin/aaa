package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.CustomerEntity;
import com.hqjy.mustang.transfer.export.model.query.SellQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gmm
 * @date create on 2018/10/22
 * @apiNote 电销商机拨打排行报表数据获取层
 */
@Component
@Mapper
public interface SellCallDao {

    /**
     * 统计接通量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countConnect(SellQueryParams params);

    /**
     * 统计接通量
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countCall(SellQueryParams params);

    /**
     * 统计通话时长
     *
     * @param params 参数
     * @return 返回结果
     */
    List<CustomerEntity> countCallTimeConnect(SellQueryParams params);
}
