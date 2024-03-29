package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_config 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/19 18:36
 */
@Mapper
public interface SysConfigDao extends BaseDao<SysConfigEntity, Long> {
    /**
     * 根据code获取系统配置信息
     */
    SysConfigEntity getConfig(String code);

    SysConfigEntity findOneByCode(String code);
}