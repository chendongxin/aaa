package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysProductDao;
import com.hqjy.mustang.admin.model.entity.SysProductEntity;
import com.hqjy.mustang.admin.service.SysProductService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.exception.RRException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service("sysProductService")
public class SysProductServiceImpl extends BaseServiceImpl<SysProductDao, SysProductEntity, Long> implements SysProductService {

    /**
     * 获取所有赛道列表
     */
    @Override
    public List<SysProductEntity> getAllProductList() {
        return baseDao.findAllProductList();
    }

    /**
     * 新增一个赛道
     */
    @Override
    public int save(SysProductEntity syaProductEntity) {
        if (baseDao.findOneByName(syaProductEntity.getName()) != null) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        syaProductEntity.setCreateUserId(getUserId());
        syaProductEntity.setCreateUserName(getUserName());
        return baseDao.save(syaProductEntity);
    }

//    /**
//     * 删除赛道
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int deleteBatch(Long[] proIds) {
//        List<Long> list = Arrays.asList(proIds);
//        for (Long proId : list) {
//
//        }
//        return super.deleteBatch(proIds);
//    }

}
