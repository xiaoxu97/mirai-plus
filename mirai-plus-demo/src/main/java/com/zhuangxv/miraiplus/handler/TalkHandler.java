package com.zhuangxv.miraiplus.handler;

import com.zhuangxv.miraiplus.annotation.MiraiPlusGroupMessageHandler;
import com.zhuangxv.miraiplus.annotation.MiraiPlusHandler;
import com.zhuangxv.miraiplus.component.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@MiraiPlusHandler
public class TalkHandler {

    private boolean isRepeat = false;

    /**
     * groupId表示限制某个群，后续会支持数组
     * senderId表示限制发言人，后续会支持数组
     * @param messagePlus 消息的封装对象
     * @return 返回值如果void则机器人什么也不做，如果返回MessagePlus，将会在对应群或者私聊回复消息。
     */
    @MiraiPlusHandler
    @MiraiPlusGroupMessageHandler(regex = ".*", groupId = 888888L, senderId = 99999L)
    public MessagePlus repeat(MessagePlus messagePlus) {
        return isRepeat ? messagePlus : null;
    }

    @MiraiPlusHandler
    @MiraiPlusGroupMessageHandler(regex = "开始复读", groupId = 888888L, senderId = 99999L)
    public void startRepeat(String message, MemberPlus sender, GroupPlus groupPlus, BotPlus botPlus) {
        System.out.println(message);
        isRepeat = true;
        groupPlus.sendMessage(new MessagePlus().at(sender).text("好的"));//主动给指定群发送消息并艾特
        botPlus.getFriend(632008666L).sendMessage(new MessagePlus().text("测试消息"));//主动给指定人发送消息
        BotPlusFactory.getBotPlus(123456L).getGroup(123456L).sendMessage("主动发送群消息");//主动获取一个机器人实例并获取一个群实例然后发送消息
    }

}
