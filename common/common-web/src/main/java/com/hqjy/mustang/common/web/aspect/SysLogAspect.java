package com.hqjy.mustang.common.web.aspect;

import com.hqjy.mustang.common.base.annotation.SysLog;
import com.hqjy.mustang.common.base.constant.RabbitQueueConstant;
import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.web.model.SysLogVO;
import com.hqjy.mustang.common.web.utils.HttpContextUtils;
import com.hqjy.mustang.common.web.utils.IPUtils;
import com.hqjy.mustang.common.web.utils.ShiroUtils;
import org.apache.catalina.session.StandardSessionFacade;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Pointcut("@annotation(com.hqjy.mustang.common.base.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {

        if (rabbitTemplate == null) {
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogVO sysLog = new SysLogVO();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] parameter = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        HashMap<String, Object> paramMap = new HashMap<>(16);
        for (int i = 0; i < parameter.length; i++) {
            if (!(parameter[i] instanceof ServletRequest) && !(parameter[i] instanceof StandardSessionFacade)) {
                paramMap.put(paramNames[i], parameter[i]);
            }
        }
        if (paramMap.size() > 0) {
            sysLog.setParams(JsonUtil.toJson(paramMap));
        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));


        if (ShiroUtils.getSubject().isAuthenticated()) {
            //用户名
            sysLog.setUsername(ShiroUtils.getUserName());
        } else {
            sysLog.setUsername(sysLog.getIp());
        }

        sysLog.setTime(time);
        // 发送MQ保存系统日志
        rabbitTemplate.convertAndSend(RabbitQueueConstant.SYS_LOG_QUEUE, JsonUtil.toJson(sysLog));
    }
}
