package com.zhuangxv.miraiplus.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxu
 * @date 2020-08-07 15:35
 */
public class BotPlusFactory {

    private static final List<BotPlus> BOT_PLUSES = new ArrayList<>();

    protected static void setBotPlus(BotPlus botPlus) {
        BOT_PLUSES.add(botPlus);
    }

    public static List<BotPlus> getBotPlus() {
        return BOT_PLUSES;
    }

    public static BotPlus getBotPlus(long botId) {
        for (BotPlus botPlus : BOT_PLUSES) {
            if (botId == botPlus.getBotId()) {
                return botPlus;
            }
        }
        return null;
    }

}
