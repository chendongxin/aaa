package com.hqjy.transfer.crm.modules.sys.service.impl;

import com.hqjy.transfer.common.base.base.BaseServiceImpl;
import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.web.utils.HttpContextUtils;
import com.hqjy.transfer.common.web.utils.IPUtils;
import com.hqjy.transfer.crm.modules.sys.dao.SysLogDao;
import com.hqjy.transfer.crm.modules.sys.model.dto.LoginUserDTO;
import com.hqjy.transfer.crm.modules.sys.model.entity.SysLogEntity;
import com.hqjy.transfer.crm.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志管理
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLogDao, SysLogEntity, Long> implements SysLogService {

    @Override
    public int save(String operation, String method, Object params) {
        SysLogEntity sysLog = new SysLogEntity();
        // 描述
        sysLog.setOperation(operation);

        //请求的方法名
        sysLog.setMethod(method);

        //请求的参数
        sysLog.setParams(JsonUtil.toJson(params));
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        if (SecurityUtils.getSubject().isAuthenticated()) {
            //用户名
            String username = ((LoginUserDTO) SecurityUtils.getSubject().getPrincipal()).getUserName();
            sysLog.setUsername(username);
        } else {
            sysLog.setUsername(sysLog.getIp());
        }
        sysLog.setTime(0L);
        //保存系统日志
        return baseDao.save(sysLog);
    }

    @Override
    public int save(String username, String operation, String method, Object params) {
        SysLogEntity sysLog = new SysLogEntity();
        // 描述
        sysLog.setOperation(operation);
        //请求的方法名
        sysLog.setMethod(method);
        //请求的参数
        sysLog.setParams(JsonUtil.toJson(params));
        //获取request
        //HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        //sysLog.setIp(IPUtils.getIpAddr(request));
        sysLog.setIp("test");
        sysLog.setUsername(username);
        sysLog.setTime(0L);
        //保存系统日志
        return baseDao.save(sysLog);
    }
}
