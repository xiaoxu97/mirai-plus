package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.contact.Member;

/**
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
}
