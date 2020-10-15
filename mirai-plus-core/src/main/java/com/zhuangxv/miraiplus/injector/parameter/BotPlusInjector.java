package com.zhuangxv.miraiplus.injector.parameter;

import com.zhuangxv.miraiplus.component.BotPlus;
import com.zhuangxv.miraiplus.component.BotPlusFactory;
import com.zhuangxv.miraiplus.component.MiraiPlusThreadLocal;
import com.zhuangxv.miraiplus.component.PlusObjectFactory;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

public class BotPlusInjector implements ObjectInjector<BotPlus> {
    @Override
    public Class<BotPlus> getType() {
        return BotPlus.class;
    }

    @Override
    public BotPlus getObject() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return BotPlusFactory.getBotPlus(event.getBot().getId());
    }
}
