package com.hqjy.mustang.admin.service.impl;

import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.admin.dao.SysDeleteLogDao;
import com.hqjy.mustang.admin.model.entity.SysDeleteLogEntity;
import com.hqjy.mustang.admin.service.SysDeleteService;
import com.hqjy.mustang.admin.utils.ShiroUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 业务删除日志
 *
 * @author : heshuangshuang
 * @date : 2018/3/29 14:56
 */
@Service
public class SysDeleteServiceImpl extends BaseServiceImpl<SysDeleteLogDao, SysDeleteLogEntity, Long> implements SysDeleteService {


    @Override
    public int saveLog(String key, String tableName, Serializable content, String memo) {
        SysDeleteLogEntity deleteLog = new SysDeleteLogEntity();
        deleteLog.setContent(JsonUtil.toJson(content));
        deleteLog.setMemo(memo);
        deleteLog.setTableName(tableName);
        deleteLog.setDeleteId(ShiroUtils.getUserId());
        deleteLog.setKeyword(key);
        return baseDao.save(deleteLog);
    }

    @Override
    public int saveLog(String tableName, Serializable content, String memo) {
        return saveLog(tableName, StringUtils.UUID(), content, memo);
    }

    @Override
    public int saveLog(String tableName, Serializable content) {
        return saveLog(tableName, content, null);
    }

    @Override
    public int saveLog(String key, String tableName, Serializable content) {
        return saveLog(key, tableName, content, null);
    }

    @Override
    public int saveLogs(String tableName, List content, String memo) {
        return saveLogs(StringUtils.UUID(), tableName, content, memo);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int saveLogs(String key, String tableName, List content, String memo) {
        final int[] count = {0};
        content.forEach(value -> {
            SysDeleteLogEntity deleteLog = new SysDeleteLogEntity();
            deleteLog.setContent(JsonUtil.toJson(value));
            deleteLog.setTableName(tableName);
            deleteLog.setDeleteId(ShiroUtils.getUserId());
            deleteLog.setMemo(memo);
            deleteLog.setKeyword(key);
            count[0] += baseDao.save(deleteLog);
        });
        return count[0];
    }

    @Override
    public int saveLogs(String tableName, List content) {
        return saveLogs(tableName, content, null);
    }
}
