package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysConfigEntity;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysConfigInfoEntity;

import java.util.List;

/**
 * 系统配置
 *
 * @author : heshuangshuang
 * @date : 2018/4/10 17:30
 */
public interface SysConfigService extends BaseService<SysConfigEntity, Long> {

    /**
     * 根据配置code获取配置值列表
     */
    List<SysConfigInfoEntity> getInfoListByCode(String code);

    /**
     * 保存配置属性
     */
    int saveInfo(SysConfigInfoEntity config);

    /**
     * 修改配置属性
     */
    int updateInfo(SysConfigInfoEntity config);

    /**
     * 删除配置属性
     */
    int deleteBatchInfo(Long[] ids);

    /**
     * 根据code获取系统配置信息
     */
    String getConfigByCode(String code);
    /**
     * 根据code获取系统配置信息
     */
    String getConfig(String code);

    /**
     * 根据code获取系统配置信息
     */
    <T> T getConfig(String code, Class<T> cass);
}
