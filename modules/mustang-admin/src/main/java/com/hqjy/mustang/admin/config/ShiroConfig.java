package com.hqjy.mustang.admin.config;

import com.hqjy.mustang.admin.shiro.AuthFilter;
import com.hqjy.mustang.admin.shiro.AuthRealm;
import com.hqjy.mustang.common.base.constant.Constant;
import com.hqjy.mustang.common.web.shiro.AuthSubject;
import com.hqjy.mustang.common.web.shiro.FilterAnonConfig;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(AuthRealm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //禁用session 的subjectFactory
        securityManager.setSubjectFactory(new AuthSubject());
        // 禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions,所以需要配合context.setSessionCreationEnabled(false);
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
        filters.put("oauth2", authFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        filterAnonConfig().getPath().forEach(path -> {
            log.info("admin 不拦截路径 => {}", path);
            filterMap.put(path, "anon");
        });
        // 不拦截内部服务调用
        filterMap.put(Constant.API_PATH_ANON , "anon");

        filterMap.put("/**", "oauth2");
        return shiroFilter;
    }

    @Bean
    public FilterAnonConfig filterAnonConfig() {
        return new FilterAnonConfig();
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
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    /**
     * 解决自定义拦截器混乱问题
     */
    @Bean
    public FilterRegistrationBean registrationAuthcFilterBean(AuthFilter sysAuthcFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(sysAuthcFilter);
        //取消自动注册功能 Filter自动注册,不会添加到FilterChain中.
        registration.setEnabled(false);
        return registration;
    }
}
