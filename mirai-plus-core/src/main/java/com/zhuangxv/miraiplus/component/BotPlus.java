package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.config.MiraiPlusConfig;
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

    private MiraiPlusConfig miraiPlusConfig;

    protected BotPlus(Bot bot) {
        this.bot = bot;
    }

    protected void setBotConfig(MiraiPlusConfig miraiPlusConfig) {
        this.miraiPlusConfig = miraiPlusConfig;
    }

    public MiraiPlusConfig getBotConfig() {
        return this.miraiPlusConfig;
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

    /**
     * 获取机器人的好友列表
     */
    public List<FriendPlus> getFriends() {
        return this.bot.getFriends().stream().map(FriendPlus::new).collect(Collectors.toList());
    }

    /**
     * 获取机器人的某个好友
     * @param qq 指定好友QQ号
     */
    public FriendPlus getFriend(long qq) {
        return new FriendPlus(this.bot.getFriend(qq));
    }

    /**
     * 获取机器人所在群列表
     */
    public List<GroupPlus> getGroups() {
       return this.bot.getGroups().stream().map(GroupPlus::new).collect(Collectors.toList());
    }

    /**
     * 获取机器人的指定群
     * @param groupId 指定群号
     */
    public GroupPlus getGroup(long groupId) {
        return new GroupPlus(this.bot.getGroup(groupId));
    }

}
