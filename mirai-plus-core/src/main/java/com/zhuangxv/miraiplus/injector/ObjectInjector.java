package com.zhuangxv.miraiplus.injector;

import net.mamoe.mirai.message.MessageEvent;

public interface ObjectInjector<T> {

    Class<T> getType();

    T getObject(MessageEvent event);

}