package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.common.base.base.BaseService;

import java.util.List;

/**
 * @author gmm
 * @date create on 2018/09/20
 * @apiNote 赛道管理
 */
public interface SysProductService extends BaseService<SysProductEntity, Long> {

    /**
     * 获取所有赛道列表
     */
    List<SysProductEntity> getAllProductList();
}
