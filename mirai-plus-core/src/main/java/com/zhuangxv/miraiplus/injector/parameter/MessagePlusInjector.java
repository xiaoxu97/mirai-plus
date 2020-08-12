package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.MessagePlus;
import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.MessageEvent;

public class MessagePlusInjector implements ObjectInjector<MessagePlus> {
    @Override
    public Class<MessagePlus> getType() {
        return MessagePlus.class;
    }

    @Override
    public MessagePlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return PlusObjectFactory.getMessagePlus(event.getMessage());
    }
}
