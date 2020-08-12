package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.MessageChain;

public class PlusObjectFactory {

    public static BotPlus getBotPlus(Bot bot) {
        return new BotPlus(bot);
    }

    public static FriendPlus getFriendPlus(Friend friend) {
        return new FriendPlus(friend);
    }

    public static MemberPlus getMemberPlus(Member member) {
        return new MemberPlus(member);
    }

    public static UserPlus getUserPlus(User user) {
        return new UserPlus(user);
    }

    public static GroupPlus getGroupPlus(Group group) {
        return new GroupPlus(group);
    }

    public static MessagePlus getMessagePlus(MessageChain messages) {
        return new MessagePlus(messages);
    }

}