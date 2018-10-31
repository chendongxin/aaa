package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.admin.model.entity.SysUserProEntity;
import com.hqjy.mustang.common.base.base.BaseService;

import java.util.List;

public interface SysUserProService extends BaseService<SysUserProEntity, Integer> {

    /**
     * 保存用户赛道关系
     */
    int save(Long userId, List<SysUserProEntity> userProList);

    /**
     * 查询用户赛道列表,包含赛道信息
     */
    List<SysUserProEntity> getUserProInfoList(Long userId);

    /**
     * 查询用户赛道Id列表
     */
    List<Long> getUserProId(Long userId);

}
