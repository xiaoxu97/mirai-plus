package com.zhuangxv.miraiplus.component;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.utils.PlatformLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author xiaoxu
 * @date 2020-08-07 11:51
 */
@Slf4j
public class MiraiPlusLog extends PlatformLogger {
    public MiraiPlusLog(@Nullable String identity, @NotNull Function1<? super String, Unit> output, boolean isColored) {
        super(identity, output, isColored);
    }

    public MiraiPlusLog(@Nullable String identity, @NotNull Function1<? super String, Unit> output) {
        super(identity, output);
    }

    public MiraiPlusLog(@NotNull Function1<? super String, Unit> output) {
        super(output);
    }

    public MiraiPlusLog(@Nullable String identity) {
        super(identity);
    }

    @Override
    public void info0(@Nullable String message) {
        log.info(message);
    }

    @Override
    public void info0(@Nullable String message, @Nullable Throwable e) {
        log.info(message, e);
    }

    @Override
    public void warning0(@Nullable String message) {
        log.warn(message);
    }

    @Override
    public void warning0(@Nullable String message, @Nullable Throwable e) {
        log.warn(message, e);
    }

    @Override
    public void error0(@Nullable String message) {
        log.error(message);
    }

    @Override
    public void error0(@Nullable String message, @Nullable Throwable e) {
        log.error(message, e);
    }

    @Override
    public void debug0(@Nullable String message) {
        log.debug(message);
    }

    @Override
    public void debug0(@Nullable String message, @Nullable Throwable e) {
        log.debug(message, e);
    }

    @Override
    public void verbose0(@Nullable String message) {
        log.debug(message);
    }

    @Override
    public void verbose0(@Nullable String message, @Nullable Throwable e) {
        log.debug(message, e);
    }
}
