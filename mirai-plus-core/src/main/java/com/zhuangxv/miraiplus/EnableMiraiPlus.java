package com.zhuangxv.miraiplus;

import com.zhuangxv.miraiplus.support.MiraiPlusRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 * @date 2020-08-07 10:43
 * 配置扫描包或者将类加入spring管理都可注册MiraiHandler
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MiraiPlusRegistrar.class)
@EnableMiraiPlusApplicationBeanContext
public @interface EnableMiraiPlus {

    String[] scanBasePackages() default {};

}
