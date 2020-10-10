package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;

/**
 * 代表一位群成员
 *
 * @author xiaoxu
 * @date 2020-08-07 16:04
 */
public class MemberPlus {

    private final Member member;

    protected MemberPlus(Member member) {
        this.member = member;
    }

    protected Member getMember() {
        return member;
    }

    /**
     * 获取群成员QQ号
     */
    public long getQQ() {
        return this.member.getId();
    }

    /**
     * 获取群成员名片
     */
    public String getNameCard() {
        return this.member.getNameCard();
    }

    /**
     * 设置群成员名片
     * @param nameCard 名片
     */
    public void setNameCard(String nameCard) {
        this.member.setNameCard(nameCard);
    }

    /**
     * 获取群成员权限
     *
     * @return 0代表一般成员 1代表管理员 2代表群主
     */
    public int getPermission() {
        return this.member.getPermission().getLevel();
    }

    /**
     * 获取群成员头衔
     */
    public String getSpecialTitle() {
        return this.member.getSpecialTitle();
    }

    /**
     * 获取被禁言剩余时长
     */
    public int getMuteTimeRemaining() {
        return this.member.getMuteTimeRemaining();
    }

    /**
     * 设置禁言
     */
    public void mute(int seconds) {
        this.member.mute(seconds);
    }

    /**
     * 解除禁言
     */
    public void unMute() {
        this.member.unmute();
    }

    /**
     * 将群成员踢出
     */
    public void kick() {
        this.member.kick();
    }

    public void kick(String message) {
        this.member.kick(message);
    }

    /**
     * 向群成员发送消息.
     * 若群成员同时是好友, 则会发送好友消息. 否则发送临时会话消息.
     * <p>
     * 单条消息最大可发送 4500 字符或 50 张图片.
     */
    public void sendMessage(MessagePlus messagePlus) {
        this.member.sendMessage(messagePlus.getMessageChain());
    }

}
