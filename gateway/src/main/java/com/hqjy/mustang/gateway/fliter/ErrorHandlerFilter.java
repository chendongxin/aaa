package com.hqjy.mustang.gateway.fliter;

import com.hqjy.mustang.common.base.utils.JsonUtil;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.Tools;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : heshuangshuang
 * @date : 2018/10/15 10:23
 */
@Slf4j
@Component
public class ErrorHandlerFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
       /* RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("this is a ErrorFilter :" + throwable.getCause().getMessage(), throwable);
        log.error("error.exception", throwable.getCause());

        log.error("网关异常，请检查微服务状态");
        ctx.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.setResponseBody(JsonUtil.toJson(R.error("网关异常，请检查微服务状态")));
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        return null;*/

        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("throwable");

            if (e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) e;
                // 删除该异常信息,不然在下一个过滤器中还会被执行处理
                ctx.remove("throwable");
                // 根据具体的业务逻辑来处理
                log.error("网关异常，请检查微服务状态：", zuulException);
                ctx.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ctx.setResponseBody(JsonUtil.toJson(R.error("网关异常，请检查微服务状态", Tools.exceptionInfo(zuulException))));
                ctx.getResponse().setContentType("application/json;charset=UTF-8");
            }
        } catch (Exception ex) {
            log.error("网关自定义错误过滤器异常:", ex);
        }
        return null;
    }
}
