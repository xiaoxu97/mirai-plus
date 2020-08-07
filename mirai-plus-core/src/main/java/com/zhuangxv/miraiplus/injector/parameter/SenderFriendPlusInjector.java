package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.FriendPlus;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;

public class SenderFriendPlusInjector implements ObjectInjector<FriendPlus> {
    @Override
    public Class<FriendPlus> getType() {
        return FriendPlus.class;
    }

    @Override
    public FriendPlus getObject(MessageEvent event) {
        if (!(event instanceof FriendMessageEvent)) {
            return null;
        }
        return PlusObjectFactory.getFriendPlus(((FriendMessageEvent) event).getSender());
    }
}
