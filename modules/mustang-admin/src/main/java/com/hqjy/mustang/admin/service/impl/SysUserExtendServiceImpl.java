package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.admin.dao.SysUserExtendDao;
import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.admin.service.SysUserExtendService;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户扩展信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:06
 */
@Service
public class SysUserExtendServiceImpl extends BaseServiceImpl<SysUserExtendDao, SysUserExtendEntity, Long> implements SysUserExtendService {

    /**
     * 根据用户Id查询,HSS 2018-07-05
     */
    @Override
    public SysUserExtendEntity findByUserId(Long userId) {
        return baseDao.findByUserId(userId);
    }
}
