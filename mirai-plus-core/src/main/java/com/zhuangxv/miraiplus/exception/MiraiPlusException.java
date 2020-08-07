package com.zhuangxv.miraiplus.exception;

import com.zhuangxv.miraiplus.component.BotPlus;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author xiaoxu
 * @date 2020-08-07 10:58
 */
public class MiraiPlusException extends RuntimeException {

    private final int code;
    private final String snapshot;
    private Object data;
    private boolean logException;

    public MiraiPlusException(Integer code, String message, String snapshotFormat, Object... argArray) {
        super(message);
        this.code = code;
        this.snapshot = MessageFormatter.arrayFormat(snapshotFormat, argArray).getMessage();
    }

    public MiraiPlusException(Integer code, String message) {
        super(message);
        this.code = code;
        this.snapshot = "无快照";
    }

    public MiraiPlusException(String message) {
        super(message);
        this.code = 0;
        this.snapshot = "无快照";
    }

    public MiraiPlusException setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MiraiPlusException logException() {
        this.logException = true;
        return this;
    }

    public boolean isLogException() {
        return logException;
    }
}
