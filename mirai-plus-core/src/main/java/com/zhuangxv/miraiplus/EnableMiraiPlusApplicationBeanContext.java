package com.zhuangxv.miraiplus;

import com.zhuangxv.miraiplus.support.MiraiPlusApplicationBeanContextRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MiraiPlusApplicationBeanContextRegistrar.class)
@interface EnableMiraiPlusApplicationBeanContext {
}
