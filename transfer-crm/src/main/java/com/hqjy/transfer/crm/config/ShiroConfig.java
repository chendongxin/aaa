package com.hqjy.transfer.crm.config;

import com.hqjy.transfer.crm.modules.sys.shiro.*;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * 完全禁用了shiro的session，shiro未管理区域session任然可用
 *
 * @author : heshuangshuang
 * @date : 2018/1/20 10:10
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(AuthRealm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //禁用session 的subjectFactory
        securityManager.setSubjectFactory(new StatelessSubject());
        //禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions,所以需要配合context.setSessionCreationEnabled(false);
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        securityManager.setRealm(oAuth2Realm);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", sysAuthcFilter());
        filters.put("api", apiFilter());
        filters.put("url", new URLFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/app/**", "anon");
        filterMap.put("/test/**", "anon");
        filterMap.put("/druid/**", "anon");

        /*  swagger  */
        filterMap.put("/doc", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/api-docs", "anon");

        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/login", "anon");

        /* 天润接口相关 */
        filterMap.put("/tinet/login", "anon");
        filterMap.put("/tinet/code", "anon");

        // 开放接口
        filterMap.put("/api/open/**", "api");

        filterMap.put("/**", "url,oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public SysAuthcFilter sysAuthcFilter() {
        return new SysAuthcFilter();
    }

    /**
     * 解决自定义拦截器混乱问题
     */
    @Bean
    public FilterRegistrationBean registrationAuthcFilterBean(SysAuthcFilter sysAuthcFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(sysAuthcFilter);
        //取消自动注册功能 Filter自动注册,不会添加到FilterChain中.
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public ApiFilter apiFilter() {
        return new ApiFilter();
    }

    /**
     * 解决自定义拦截器混乱问题
     */
    @Bean
    public FilterRegistrationBean registrationApiFilterBean(ApiFilter apiFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(apiFilter);
        //取消自动注册功能 Filter自动注册,不会添加到FilterChain中.
        registration.setEnabled(false);
        return registration;
    }

}
