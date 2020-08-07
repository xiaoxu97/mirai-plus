package com.zhuangxv.miraiplus.annotation;

import java.lang.annotation.*;

/**
 * @author xiaoxu
 * @date 2020-08-07 11:09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MiraiPlusHandler {

    /**
     * 指定某个机器人
     */
    String botName() default "none";

}
