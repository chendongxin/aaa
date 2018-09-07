package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.common.base.base.BaseService;

/**
 * 用户扩展信息
 *
 * @author : heshuangshuang
 * @date : 2018/9/7 16:06
 */
public interface SysUserExtendService extends BaseService<SysUserExtendEntity, Long> {

    /**
     * 根据用户Id查询,HSS 2018-07-05
     */
    SysUserExtendEntity findByUserId(Long userId);
}
