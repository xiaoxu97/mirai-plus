package com.zhuangxv.miraiplus.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * @author xiaoxu
 */
@Slf4j
public class BeanRegistryUtils {

    public BeanRegistryUtils() {
    }

    public static void registerBeans(BeanDefinitionRegistry registry, Class<?>... annotatedClasses) {
        if (!ObjectUtils.isEmpty(annotatedClasses)) {
            boolean debugEnabled = log.isDebugEnabled();
            AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
            if (debugEnabled) {
                log.debug(registry.getClass().getSimpleName() + " will register annotated classes : " + Arrays.asList(annotatedClasses) + " .");
            }
            reader.register(annotatedClasses);
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> annotatedClass, String beanName) {
        registry.registerBeanDefinition(beanName, BeanDefinitionBuilder.rootBeanDefinition(annotatedClass).getBeanDefinition());
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> annotatedClass) {
        registerBeanDefinition(registry, annotatedClass, StringUtils.getSimpleClassName(annotatedClass));
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, BeanDefinition beanDefinition, String beanName) {
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

}
