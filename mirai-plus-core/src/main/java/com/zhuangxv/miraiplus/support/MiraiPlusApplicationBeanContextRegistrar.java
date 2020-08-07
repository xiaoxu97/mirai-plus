package com.zhuangxv.miraiplus.support;

import com.zhuangxv.miraiplus.util.MiraiPlusApplicationBeanContext;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhuan
 */
public class MiraiPlusApplicationBeanContextRegistrar implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{MiraiPlusApplicationBeanContext.class.getName()};
    }
}
