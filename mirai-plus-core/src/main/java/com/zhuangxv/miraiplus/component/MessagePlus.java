package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

public class MessagePlus {

    private MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

    public MessagePlus at(MemberPlus memberPlus) {
        this.messageChainBuilder.add(new At(memberPlus.getMember()));
        return this;
    }

    public MessagePlus text(String message) {
        this.messageChainBuilder.add(new PlainText(message));
        return this;
    }

    protected MessageChain getMessageChain() {
        return this.messageChainBuilder.build();
    }

}