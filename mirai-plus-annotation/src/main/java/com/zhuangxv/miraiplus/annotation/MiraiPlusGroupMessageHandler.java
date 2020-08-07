package com.zhuangxv.miraiplus.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 * @date 2020-08-07 16:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MiraiPlusGroupMessageHandler {

    /**
     * 匹配正则
     */
    String regex() default "none";

    /**
     * 限制某个群
     */
    long groupId() default 0;

    /**
     * 限制发言人
     */
    long senderId() default 0;

}
