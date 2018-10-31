package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.admin.model.entity.SysUserProEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sys_user_pro 持久化层
 * 
 * @author : xyq
 * @date : 2018/10/29 10:36
 */
@Mapper
public interface SysUserProDao extends BaseDao<SysUserProEntity, Integer> {

    /**
     * 删除用户赛道关系
     * @author : gmm
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 查询用户赛道列表,包含赛道信息
     */
    List<SysUserProEntity> findUserProInfoList(Long userId);

    /**
     * 查询用户赛道ID列表
     */
    List<Long> findProIdByUserId(Long userId);

}