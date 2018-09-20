package com.hqjy.mustang.admin.dao;
import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_product 持久化层
 * 
 * @author : xyq
 * @date : 2018/09/18 20:03
 */
@Mapper
public interface SysProductDao extends BaseDao<SysProductEntity, Long> {
}