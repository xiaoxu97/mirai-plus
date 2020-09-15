package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.List;
import java.util.stream.Collectors;

public class GroupPlus {

    private final Group group;

    protected GroupPlus(Group group) {
        this.group = group;
    }

    public Long getId() {
        return this.group.getId();
    }

    public String getName() {
        return this.group.getName();
    }

    public String getAvatarUrl() {
        return this.group.getAvatarUrl();
    }

    public MemberPlus getOwner() {
        return new MemberPlus(this.group.getOwner());
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

    public List<MemberPlus> getMemberPlusList() {
        return this.group.getMembers().stream().map(MemberPlus::new).collect(Collectors.toList());
    }

}
