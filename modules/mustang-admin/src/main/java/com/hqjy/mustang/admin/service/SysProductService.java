package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.common.base.base.BaseService;

import java.util.List;

public interface SysProductService extends BaseService<SysProductEntity, Long> {

    /**
     * 获取所有赛道列表
     */
    List<SysProductEntity> getAllProductList();
}
