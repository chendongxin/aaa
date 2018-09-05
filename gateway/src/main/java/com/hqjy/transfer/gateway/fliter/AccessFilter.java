package com.hqjy.transfer.gateway.fliter;

import com.hqjy.transfer.common.base.constant.Constant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求过滤器
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date :  2018/8/18 16:45
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        if (request.getRequestURL().toString().contains(Constant.API_PATH)) {
            log.warn("不允许访问微服务内部接口");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("pass ok");
        return null;
    }
}
