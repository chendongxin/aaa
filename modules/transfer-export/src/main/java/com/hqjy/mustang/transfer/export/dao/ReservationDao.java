package com.hqjy.mustang.transfer.export.dao;


import com.hqjy.mustang.transfer.export.model.entity.ReservationExportEntity;
import com.hqjy.mustang.transfer.export.model.query.ReservationQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : xyq
 * @date : 2018/09/14 11:19
 */
@Mapper
@Component
public interface ReservationDao {

    /**
     * 获取邀约报表导出数据
     *
     * @param params 插叙参数
     * @return 返回查询结果集
     */
    List<ReservationExportEntity> getExportData(ReservationQueryParams params);
}