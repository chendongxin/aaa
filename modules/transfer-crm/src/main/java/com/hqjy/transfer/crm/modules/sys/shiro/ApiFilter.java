package com.hqjy.transfer.crm.modules.sys.shiro;

import com.hqjy.transfer.common.base.constant.ConfigConstant;
import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.common.base.utils.StringUtils;
import com.hqjy.transfer.common.base.utils.Tools;
import com.hqjy.transfer.crm.modules.sys.service.SysConfigService;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * api调用授权认证
 *
 * @author :heshuangshuang
 * @date :2018/1/20 10:10
 */
public class ApiFilter extends AdviceFilter {

    @Autowired
    private SysConfigService sysConfigService;

    private final static String API_TOKEN_TIMESTAMP = "api-token-timestamp";
    private final static String API_TOKEN_DIGEST = "api-token-digest";
    /**
     * api请求有效时间1个小时
     */
    private final static long API_EXPIRE = 60 * 60 * 1000;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //从header中获取apiToken
        String apiTimestamp = httpRequest.getHeader(API_TOKEN_TIMESTAMP);
        if (StringUtils.isNotEmpty(apiTimestamp)) {
            try {
                long times = Long.valueOf(apiTimestamp.substring(0, 13));
                // 验证有效期
                if (System.currentTimeMillis() - times < API_EXPIRE) {
                    String apiSecret = httpRequest.getHeader(API_TOKEN_DIGEST);
                    if (StringUtils.isNotEmpty(apiSecret)) {
                        String secret = sysConfigService.getConfig(ConfigConstant.API_SECRET);
                        if (apiSecret.equals(Tools.sha1Hex(apiTimestamp, secret))) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String result = JsonUtil.toJson(R.error("未通过授权验证"));
        try {
            httpResponse.setContentType("application/json;charset=utf-8");
            //处理登录失败的异常
            httpResponse.getWriter().print(result);
        } catch (IOException ignored) {

        }
        return false;
    }
}
