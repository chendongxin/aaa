package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysProductDao;
import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.admin.service.SysProductService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysProductService")
public class SysProductServiceImpl extends BaseServiceImpl<SysProductDao, SysProductEntity, Long> implements SysProductService {

    /**
     * 获取所有赛道列表
     */
    @Override
    public List<SysProductEntity> getAllProductList() {
        return baseDao.findAllProductList();
    }
}
