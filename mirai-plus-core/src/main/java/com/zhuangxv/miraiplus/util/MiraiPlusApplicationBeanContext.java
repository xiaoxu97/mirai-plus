package com.zhuangxv.miraiplus.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author zhuan
 */
@Slf4j
public class MiraiPlusApplicationBeanContext implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MiraiPlusApplicationBeanContext.applicationContext == null) {
            MiraiPlusApplicationBeanContext.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBeanByName(String name) {
        if (applicationContext == null || !applicationContext.containsBean(name)) {
            return null;
        }
        return applicationContext.getBean(name);
    }

    public static <T> T getBeanByClass(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBeanByName(String name, Class<T> clazz) {
        if (applicationContext == null || !applicationContext.containsBean(name)) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansByClass(Class<T> tClass) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeansOfType(tClass);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> aClass) {
        return applicationContext.getBeansWithAnnotation(aClass);
    }

    @Override
    public void destroy() throws Exception {
        log.info("clear ApplicationContext:" + applicationContext);
        applicationContext = null;
    }
}
