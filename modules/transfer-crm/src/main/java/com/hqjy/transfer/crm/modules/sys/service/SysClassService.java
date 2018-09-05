package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysClassEntity;

import java.util.List;

/**
 * @author xieyuqing
 * @ description 班次管理业务层
 * @ date create in 2018年5月23日14:20:13
 */
public interface SysClassService extends BaseService<SysClassEntity, Long> {


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
    SysClassEntity findClassByName(String className, Long classId);
}
