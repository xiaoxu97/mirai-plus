package com.zhuangxv.miraiplus.support;

import com.zhuangxv.miraiplus.EnableMiraiPlus;
import com.zhuangxv.miraiplus.annotation.MiraiPlusHandler;
import com.zhuangxv.miraiplus.component.MiraiPlusInit;
import com.zhuangxv.miraiplus.component.MiraiPlusMessageDispatcher;
import com.zhuangxv.miraiplus.config.ConfigKeys;
import com.zhuangxv.miraiplus.config.MiraiPlusConfig;
import com.zhuangxv.miraiplus.config.MiraiPlusConfigFactory;
import com.zhuangxv.miraiplus.exception.MiraiPlusException;
import com.zhuangxv.miraiplus.util.BeanRegistryUtils;
import com.zhuangxv.miraiplus.util.ClassUtils;
import com.zhuangxv.miraiplus.util.PropertySourcesUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxu
 * @date 2020-08-07 10:44
 */
@Slf4j
public class MiraiPlusRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, environment);
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, @NotNull BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder configFactory = BeanDefinitionBuilder.rootBeanDefinition(MiraiPlusConfigFactory.class);
        configFactory.addPropertyValue("miraiPlusConfigs", this.loadConfig());
        BeanRegistryUtils.registerBeanDefinition(beanDefinitionRegistry, configFactory.getBeanDefinition(), "miraiPlusConfigFactory");
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableMiraiPlus.class.getName()));
        Assert.notNull(attributes, "Mirai plus attributes is must be not null!");
        String[] scanBasePackages = attributes.getStringArray("scanBasePackages");
        for (String scanBasePackage : scanBasePackages) {
            for (String className : ClassUtils.getClassName(scanBasePackage, true)) {
                try {
                    Class<?> aClass = Class.forName(className);
                    if (aClass.getAnnotation(MiraiPlusHandler.class) != null) {
                        BeanRegistryUtils.registerBeanDefinition(beanDefinitionRegistry, aClass);
                    }
                } catch (ClassNotFoundException e) {
                    log.error("class not found", e);
                }
            }
        }
        for (String className : ClassUtils.getClassName("com.zhuangxv.miraiplus.injector.parameter", true)) {
            try {
                Class<?> aClass = Class.forName(className);
                BeanRegistryUtils.registerBeanDefinition(beanDefinitionRegistry, aClass);
            } catch (ClassNotFoundException e) {
                log.error("class not found", e);
            }
        }
        BeanRegistryUtils.registerBeanDefinition(beanDefinitionRegistry, BeanDefinitionBuilder.rootBeanDefinition(MiraiPlusInit.class).getBeanDefinition(), "miraiPlusInit");
        BeanRegistryUtils.registerBeanDefinition(beanDefinitionRegistry, BeanDefinitionBuilder.rootBeanDefinition(MiraiPlusMessageDispatcher.class).getBeanDefinition(), "miraiPlusMessageDispatcher");
    }

    private List<MiraiPlusConfig> loadConfig() {
        List<MiraiPlusConfig> miraiPlusConfigs = new ArrayList<>();
        if (PropertySourcesUtils.getSubProperties(this.environment.getPropertySources(), ConfigKeys.MIRAI_SINGLE_KEY).isEmpty()
                && PropertySourcesUtils.getSubProperties(this.environment.getPropertySources(), ConfigKeys.MIRAI_MULTIPLE_KEY).isEmpty()) {
            throw new MiraiPlusException("未发现配置文件。");
        } else {
            Binder binder = Binder.get(this.environment);
            if (!PropertySourcesUtils.getSubProperties(this.environment.getPropertySources(), ConfigKeys.MIRAI_SINGLE_KEY).isEmpty()) {
                miraiPlusConfigs.add(binder.bind(ConfigKeys.MIRAI_SINGLE_KEY, Bindable.of(MiraiPlusConfig.class)).get());
            } else {
                miraiPlusConfigs.addAll(binder.bind(ConfigKeys.MIRAI_SINGLE_KEY, Bindable.listOf(MiraiPlusConfig.class)).get());
            }
            return miraiPlusConfigs;
        }
    }
}
