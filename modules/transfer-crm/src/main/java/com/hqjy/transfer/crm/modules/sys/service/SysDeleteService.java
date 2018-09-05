package com.hqjy.transfer.crm.modules.sys.service;

import com.hqjy.transfer.common.base.base.BaseService;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysDeleteLogEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author : heshuangshuang
 * @date : 2018/3/29 14:56
 */
public interface SysDeleteService extends BaseService<SysDeleteLogEntity, Long> {

    int saveLog(String key, String tableName, Serializable content, String memo);

    int saveLog(String tableName, Serializable content, String memo);

    int saveLog(String tableName, Serializable content);

    int saveLog(String key, String tableName, Serializable content);

    /**
     * 批量删除，插入日志
     */
    int saveLogs(String tableName, List content, String memo);

    int saveLogs(String key, String tableName, List content, String memo);

    /**
     * 批量删除，插入日志
     */
    int saveLogs(String tableName, List content);


}
