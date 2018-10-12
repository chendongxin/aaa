package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.transfer.export.model.entity.ReservationExportEntity;
import com.hqjy.mustang.transfer.export.model.query.ReservationQueryParams;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/10/10
 * @apiNote
 */
public interface ReservationService {


    /**
     * 获取邀约报表导出数据
     *
     * @param params 请求参数
     * @return 返回查询结果集
     */
    List<ReservationExportEntity> getExportData(ReservationQueryParams params);


    /**
     * 导出邀约报表
     *
     * @param params 请求参数
     * @return 返回文件下载地址
     */
    String exportReservation(ReservationQueryParams params);
}
