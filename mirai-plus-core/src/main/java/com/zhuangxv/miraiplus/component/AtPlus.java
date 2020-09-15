package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.exception.MiraiPlusException;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.SingleMessage;

public class AtPlus {

    private final At at;

    protected AtPlus(At at) {
        this.at = at;
    }

    protected AtPlus(SingleMessage at) {
        if (!(at instanceof At)) {
            throw new MiraiPlusException(999, "不支持的Image对象");
        }
        this.at = (At) at;
    }

    public MemberPlus getMemberPlus() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (!(event instanceof GroupMessageEvent)) {
            throw new MiraiPlusException(999, "当前消息非群消息。");
        }
        return new MemberPlus(((GroupMessageEvent) event).getGroup().get(this.at.getTarget()));
    }

}
