package com.hqjy.mustang.transfer.export.dao;


import com.hqjy.mustang.transfer.export.model.entity.CustomerExportEntity;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : gmm
 * @date : 2018/10/12 11:19
 */
@Mapper
@Component
public interface CustomerDao {

    /**
     * 获取客户报表导出数据
     *
     * @param query 插叙参数
     * @return 返回查询结果集
     */
    List<CustomerExportEntity> getExportData(CustomerExportQueryParams query);
}
