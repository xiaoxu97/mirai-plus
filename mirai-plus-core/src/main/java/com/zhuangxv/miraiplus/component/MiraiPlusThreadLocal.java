package com.zhuangxv.miraiplus.component;

import net.mamoe.mirai.message.MessageEvent;

public class MiraiPlusThreadLocal {

    public static ThreadLocal<MessageEvent> messageEvent = new ThreadLocal<>();

}
