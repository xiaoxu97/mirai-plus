package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.component.UserPlus;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.MessageEvent;

public class SenderUserPlusInjector implements ObjectInjector<UserPlus> {
    @Override
    public Class<UserPlus> getType() {
        return UserPlus.class;
    }

    @Override
    public UserPlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return PlusObjectFactory.getUserPlus(event.getSender());
    }
}
