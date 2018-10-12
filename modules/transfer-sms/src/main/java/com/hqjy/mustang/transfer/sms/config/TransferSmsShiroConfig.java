package com.hqjy.mustang.transfer.sms.config;

import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.common.base.utils.StringUtils;
import com.hqjy.mustang.common.web.model.AuthCheckResult;
import com.hqjy.mustang.common.web.shiro.AuthRealm;
import com.hqjy.mustang.common.web.shiro.AuthShiroConfig;
import com.hqjy.mustang.common.web.shiro.AuthToken;
import com.hqjy.mustang.common.web.shiro.FilterAnonConfig;
import com.hqjy.mustang.common.web.utils.ResponseUtils;
import com.hqjy.mustang.common.web.utils.TokenUtils;
import com.hqjy.mustang.transfer.sms.fegin.AuthApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/17 23:22
 */
@Configuration
@Slf4j
public class TransferSmsShiroConfig extends AuthShiroConfig {

    private static final String AUTH_NAME = "auth";

    /**
     * 自定义realms
     */
    @Override
    @Bean
    protected List<Realm> getRealms() {
        List<Realm> list = new ArrayList<>();
        list.add(new AuthRealm());
        return list;
    }

    /**
     * 注入自定义拦截器,注意拦截器自注入问题
     */
    @Override
    @Bean
    protected Map<String, Filter> getFilters() {
        Map<String, Filter> list = new HashMap<>();
        list.put(AUTH_NAME, authFilter());
        return list;
    }

    /**
     * 拦截器链
     */
    @Override
    protected Map<String, String> getFilterChainDefinitionMap() {
        // 拦截器链
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterAnonConfig().getPath().forEach(path -> {
            log.info("transfer-call 不拦截路径 => {}", path);
            filterMap.put(path, "anon");
        });
        // 不拦截内部服务调用
        filterMap.put(Constant.API_PATH_ANON, "anon");
        filterMap.put(Constant.API_OPEN_PATH_ANON, "anon");

        filterMap.put("/**", AUTH_NAME);
        return filterMap;
    }

    @Bean
    public FilterAnonConfig filterAnonConfig() {
        return new FilterAnonConfig();
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    /**
     * 解决自定义拦截器混乱问题
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean registrationAuthcFilterBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(authFilter());
        //取消自动注册功能 Filter自动注册,不会添加到FilterChain中.
        registration.setEnabled(false);
        return registration;
    }

    private class AuthFilter extends AccessControlFilter {

        @Autowired
        private AuthApiService authApiService;

        @Override
        protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
            return false;
        }

        /**
         * 验证用户token
         */
        @Override
        protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
            String userToken = ((HttpServletRequest) request).getHeader(Constant.AUTHOR_PARAM);
            if (StringUtils.isNotEmpty(userToken)) {
                try {
                    TokenUtils.verify(userToken, Constant.JWT_SIGN_KEY);
                    Long userId = TokenUtils.tokenInfo(userToken, Constant.JWT_TOKEN_USERID, Long.class);
                    String jti = TokenUtils.tokenInfo(userToken, Constant.JWT_ID, String.class);
                    AuthCheckResult result = authApiService.checkToken(userId, jti);
                    if (result != null) {
                        switch (result.getCheck()) {
                            case SUCCESS:
                                String userName = TokenUtils.tokenInfo(userToken, Constant.JWT_TOKEN_USERNAME, String.class);
                                getSubject(request, response).login(new AuthToken(userId, userName, userToken, result.getDeptSet(), result.getRoleSet(), result.getPermSet()));
                                return true;
                            case TOKEN_OUT:
                                ResponseUtils.response(response, HttpStatus.OK.value(), R.error(StatusCode.TOKEN_OUT));
                                return false;
                            case TOKEN_OVERDUE:
                                ResponseUtils.response(response, HttpStatus.OK.value(), R.error(StatusCode.TOKEN_OVERDUE));
                                return false;
                            default:
                        }
                    }
                } catch (Exception ignored) {
                }
            }
            ResponseUtils.response(response, HttpStatus.FORBIDDEN.value(), R.error(StatusCode.TOKEN_FAULT));
            log.debug("URL:{},  userToken 验证失败:{}", ((HttpServletRequest) request).getRequestURL(), userToken);
            return false;
        }
    }

}
