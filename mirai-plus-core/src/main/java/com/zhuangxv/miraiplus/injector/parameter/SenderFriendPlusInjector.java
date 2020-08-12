package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.FriendPlus;
import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.MessageEvent;

public class SenderFriendPlusInjector implements ObjectInjector<FriendPlus> {
    @Override
    public Class<FriendPlus> getType() {
        return FriendPlus.class;
    }

    @Override
    public FriendPlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (!(event instanceof FriendMessageEvent)) {
            return null;
        }
        return PlusObjectFactory.getFriendPlus(((FriendMessageEvent) event).getSender());
    }
}
