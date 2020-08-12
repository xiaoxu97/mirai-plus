package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.GroupPlus;
import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;

public class GroupPlusInjector implements ObjectInjector<GroupPlus> {
    @Override
    public Class<GroupPlus> getType() {
        return GroupPlus.class;
    }

    @Override
    public GroupPlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (!(event instanceof GroupMessageEvent)) {
            return null;
        }
        return PlusObjectFactory.getGroupPlus(((GroupMessageEvent) event).getGroup());
    }
}
