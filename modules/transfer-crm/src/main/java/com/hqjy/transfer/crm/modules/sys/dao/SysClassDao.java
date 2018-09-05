package com.hqjy.transfer.crm.modules.sys.dao;

import com.hqjy.transfer.common.base.base.BaseDao;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_class 持久化层
 * 
 * @author : HejinYo   hejinyo@gmail.com 
 * @date : 2018/05/23 14:13
 */
@Mapper
public interface SysClassDao extends BaseDao<SysClassEntity, Long> {

    /**
     * 获取所有班次管理配置作为下拉框列表
     * @return 返回所有结果
     */
    List<SysClassEntity> getClassComboBox();
    /**
     * 判断是否存在相同名称的班次
     * @param className 班次名称
     * @return 返回结果
     */
    SysClassEntity findClassByName(@Param("className") String className, @Param("classId") Long classId);
}