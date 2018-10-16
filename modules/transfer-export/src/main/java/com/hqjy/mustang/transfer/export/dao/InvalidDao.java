package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.InvalidExportEntity;
import com.hqjy.mustang.transfer.export.model.query.InvalidQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : gmm
 * @date : 2018/10/12 11:19
 */
@Mapper
@Component
public interface InvalidDao {

    /**
     * 获取客户报表导出数据
     *
     * @param query 插叙参数
     * @return 返回查询结果集
     */
    List<InvalidExportEntity> getExportData(InvalidQueryParams query);
}
