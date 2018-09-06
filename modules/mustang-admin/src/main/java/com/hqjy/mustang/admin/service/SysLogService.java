package com.hqjy.mustang.admin.service;

import com.hqjy.mustang.common.base.base.BaseService;
import com.hqjy.mustang.admin.model.entity.SysLogEntity;

/**
 * 系统日志
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
public interface SysLogService extends BaseService<SysLogEntity, Long> {

    int save(String operation, String method, Object params);

    int save(String username, String operation, String method, Object params);

}
