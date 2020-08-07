package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaoxu
 * @date 2020-08-07 15:35
 */
public class BotPlus {

    private final Bot bot;

    private String botName;

    protected BotPlus(Bot bot) {
        this.bot = bot;
    }

    public List<FriendPlus> getFriends() {
        Collection<Friend> friends = this.bot.getFriends();
        return friends.stream().map(FriendPlus::new).collect(Collectors.toList());
    }

    public String getBotName() {
        return this.botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public long getBotId() {
        return this.bot.getId();
    }
}
