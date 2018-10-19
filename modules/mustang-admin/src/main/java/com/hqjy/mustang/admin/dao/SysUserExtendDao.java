package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.admin.model.entity.SysUserExtendEntity;
import com.hqjy.mustang.common.base.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户扩展信息
 * sys_user_extend 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/09/07 16:04
 */
@Mapper
public interface SysUserExtendDao extends BaseDao<SysUserExtendEntity, Long> {

    /**
     * 根据用户id获取扩展信息
     */
    SysUserExtendEntity findByUserId(Long userId);

    /**
     * 获取所有已存在的TQ账号信息
     */
    List<SysUserExtendEntity> getExistTqId();
}