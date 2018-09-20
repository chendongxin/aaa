package com.hqjy.mustang.admin.dao;
import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sys_product 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/18 20:03
 */
@Mapper
public interface SysProductDao extends BaseDao<SysProductEntity, Long> {

    /**
     * 所有部门赛道列表
     */
    List<SysProductEntity> findAllProductList();
}