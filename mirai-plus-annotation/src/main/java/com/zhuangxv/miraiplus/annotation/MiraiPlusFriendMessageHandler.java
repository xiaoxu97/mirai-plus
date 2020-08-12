package com.zhuangxv.miraiplus.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 * @date 2020-08-07 16:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MiraiPlusFriendMessageHandler {
    /**
     * 匹配正则
     */
    String regex() default "none";

    /**
     * 限制发言人
     */
    long senderId() default 0;

}
