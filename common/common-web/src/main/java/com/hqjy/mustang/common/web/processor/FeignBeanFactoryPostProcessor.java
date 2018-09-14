package com.hqjy.mustang.common.web.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 处理spring cloud 测试的时候报 BeanCreationNotAllowedException:
 * Error creating bean with name 'eurekaAutoServiceRegistration'
 *
 * @author xyq 2018年9月12日15:01:10
 */
@Component
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final String FEIGN_CONTEXT="feignContext";
    private static final String EUREKA_AUTO_SERVICE_REGISTRATION="eurekaAutoServiceRegistration";
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (containsBeanDefinition(beanFactory, FEIGN_CONTEXT, EUREKA_AUTO_SERVICE_REGISTRATION)) {
            BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
            bd.setDependsOn("eurekaAutoServiceRegistration");
        }
    }

    private boolean containsBeanDefinition(ConfigurableListableBeanFactory beanFactory, String... beans) {
        return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
    }
}