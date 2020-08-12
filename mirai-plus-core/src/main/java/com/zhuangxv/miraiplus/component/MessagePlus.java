package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.enums.FacePlus;
import com.zhuangxv.miraiplus.exception.MiraiPlusException;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.*;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MessagePlus {

    private final MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

    public MessagePlus() {

    }

    protected MessagePlus(MessageChain messages) {
        this.messageChainBuilder.addAll(messages);
    }

    public MessagePlus at(MemberPlus memberPlus) {
        this.messageChainBuilder.add(new At(memberPlus.getMember()));
        return this;
    }

    public MessagePlus at(long qq) {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (!(event instanceof GroupMessageEvent)) {
            throw new MiraiPlusException(999, "不支持的消息类型");
        }
        this.messageChainBuilder.add(new At(((GroupMessageEvent) event).getGroup().get(qq)));
        return this;
    }

    public MessagePlus atAll() {
        this.messageChainBuilder.add(AtAll.INSTANCE);
        return this;
    }

    public MessagePlus text(String message) {
        this.messageChainBuilder.add(new PlainText(message));
        return this;
    }

    public MessagePlus face(FacePlus facePlus) {
        this.messageChainBuilder.add(new Face(facePlus.getFaceId()));
        return this;
    }

    public MessagePlus image(String image) {
        try {
            this.messageChainBuilder.add(this.getImage(new URL(image)));
        } catch (MalformedURLException e) {
            throw new MiraiPlusException(e.hashCode(), e.getMessage());
        }
        return this;
    }

    public MessagePlus image(File image) {
        this.messageChainBuilder.add(this.getImage(image));
        return this;
    }

    public MessagePlus image(InputStream image) {
        this.messageChainBuilder.add(this.getImage(image));
        return this;
    }

    public MessagePlus flashImage(String image) {
        try {
            this.messageChainBuilder.add(FlashImage.from(this.getImage(new URL(image))));
        } catch (Exception e) {
            throw new MiraiPlusException(e.hashCode(), e.getMessage());
        }
        return this;
    }

    public MessagePlus flashImage(File image) {
        this.messageChainBuilder.add(FlashImage.from(this.getImage(image)));
        return this;
    }

    public MessagePlus flashImage(InputStream image) {
        this.messageChainBuilder.add(FlashImage.from(this.getImage(image)));
        return this;
    }


    public String getString() {
        return this.messageChainBuilder.build().contentToString();
    }

    protected MessageChain getMessageChain() {
        return this.messageChainBuilder.build();
    }

    private Image getImage(URL image) {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return event.getSender().uploadImage(image);
    }

    private Image getImage(File image) {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return event.getSender().uploadImage(image);
    }

    private Image getImage(InputStream image) {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        return event.getSender().uploadImage(image);
    }

}