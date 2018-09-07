package com.hqjy.mustang.common.web.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/17 23:22
 */
public abstract class AuthShiroConfig {

    /**
     * SecurityManager 安全管理器 有多个Realm,可使用'realms'属性代替
     */
    @Bean("securityManager")
    protected SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 禁用session 的subjectFactory
        securityManager.setSubjectFactory(new AuthSubject());
        // 禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions,所以需要配合context.setSessionCreationEnabled(false);
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        // 自定义realms
        securityManager.setRealms(getRealms());
        return securityManager;
    }

    /**
     * 自定义realms
     */
    abstract protected List<Realm> getRealms();


    @Bean("shiroFilter")
    protected ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        //自定义访问验证拦截器
        factoryBean.setFilters(getFilters());
        // 拦截器链
        factoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
        return factoryBean;
    }

    /**
     * 注入自定义拦截器,注意拦截器自注入问题
     */
    abstract protected Map<String, Filter> getFilters();

    /**
     * 拦截器链
     */
    abstract protected Map<String, String> getFilterChainDefinitionMap();


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


}
