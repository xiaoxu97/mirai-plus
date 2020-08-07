package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.MessageEvent;

public class MessageInjector implements ObjectInjector<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String getObject(MessageEvent event) {
        return event.getMessage().contentToString();
    }
}
