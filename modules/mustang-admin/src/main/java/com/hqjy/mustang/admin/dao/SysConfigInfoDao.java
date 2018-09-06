package com.hqjy.mustang.admin.dao;

import com.hqjy.mustang.common.base.base.BaseDao;
import com.hqjy.mustang.admin.model.entity.SysConfigInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_config_info 持久化层
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2018/05/19 18:36
 */
@Mapper
public interface SysConfigInfoDao extends BaseDao<SysConfigInfoEntity, Long> {
    /**
     * 修改配置属性编码
     */
    int updateCode(@Param("oldCode") String oldCode, @Param("newCode") String newCode);

    /**
     * 根据配置code获取配置值列表
     */
    List<SysConfigInfoEntity> findInfoListByCode(String code);
}