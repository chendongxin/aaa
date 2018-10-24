package com.hqjy.mustang.transfer.export.dao;

import com.hqjy.mustang.transfer.export.model.entity.SmsCostEntity;
import com.hqjy.mustang.transfer.export.model.query.SmsCostQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xyq
 * @date create on 2018/9/17
 * @apiNote 招转短信费用数据获取层
 */
@Component
@Mapper
public interface PromotionSmsCostDao {

    /**
     * 获取推广招转短信费用
     *
     * @param params 查询参数
     * @return 返回查询结果集
     */
    List<SmsCostEntity> getData(SmsCostQueryParams params);
}
