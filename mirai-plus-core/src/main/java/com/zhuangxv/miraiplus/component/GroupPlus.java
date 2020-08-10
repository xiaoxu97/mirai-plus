package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

public class GroupPlus {

    private final Group group;

    protected GroupPlus(Group group) {
        this.group = group;
    }

    public void sendMessage(MessagePlus messagePlus) {
        this.group.sendMessage(messagePlus.getMessageChain());
    }

    public void sendMessage(String message) {
        this.group.sendMessage(message);
    }

    public MemberPlus getMemberPlus(long qq) {
        return new MemberPlus(this.group.get(qq));
    }

}
