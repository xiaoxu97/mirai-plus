package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.config.MiraiPlusConfig;
import com.zhuangxv.miraiplus.config.MiraiPlusConfigFactory;
import kotlinx.serialization.json.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaoxu
 * @date 2020-08-07 11:12
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MiraiPlusInit implements CommandLineRunner {
    private final MiraiPlusMessageDispatcher dispatcher;
    private final MiraiPlusConfigFactory miraiPlusConfigFactory;

    private final Lock blockLock = new ReentrantLock(true);
    private final Condition blockCondition = blockLock.newCondition();

    @Override
    public void run(String... args) {
        List<MiraiPlusConfig> miraiPlusConfigs = this.miraiPlusConfigFactory.getMiraiPlusConfigs();
        miraiPlusConfigs.forEach(v -> {
            Bot bot = BotFactoryJvm.newBot(v.getBotQQ(), v.getBotPassword(), new BotConfiguration() {
                {
                    setDeviceInfo(context ->
                            SystemDeviceInfoKt.loadAsDeviceInfo(new File(v.getDeviceInfoPath()), Json.Default, context)
                    );
                    setBotLoggerSupplier(bot -> new MiraiPlusLog(String.valueOf(bot.getId())));
                    setNetworkLoggerSupplier(bot -> new MiraiPlusLog(bot.getId() + "-network"));
                    setProtocol(MiraiProtocol.ANDROID_PAD);
                }
            });
            bot.login();
            BotPlusFactory.setBotPlus(new BotPlus(bot) {
                {
                    setBotName(v.getBotName());
                }
            });
            Events.registerEvents(bot, this.dispatcher);
            log.info(v.getBotName() + "为您服务。");
        });
        this.dispatcher.init();
        this.block();
    }

    private void block() {
        Thread awaitThread = new Thread(() -> {
            blockLock.lock();
            try {
                blockCondition.await();
            } catch (InterruptedException ignore) {
            }
            blockLock.unlock();
        });
        awaitThread.setContextClassLoader(getClass().getClassLoader());
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
