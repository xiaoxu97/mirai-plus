package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.MemberPlus;
import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;

public class SenderMemberPlusInjector implements ObjectInjector<MemberPlus> {
    @Override
    public Class<MemberPlus> getType() {
        return MemberPlus.class;
    }

    @Override
    public MemberPlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (event instanceof GroupMessageEvent) {
            return PlusObjectFactory.getMemberPlus(((GroupMessageEvent) event).getSender());
        }
        if (event instanceof TempMessageEvent) {
            return PlusObjectFactory.getMemberPlus(((TempMessageEvent) event).getSender());
        }
        return null;
    }
}
