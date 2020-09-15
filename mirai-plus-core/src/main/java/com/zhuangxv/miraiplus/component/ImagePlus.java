package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.exception.MiraiPlusException;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.GroupImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalImageJvmKt;

import java.io.File;

public class ImagePlus {

    private final Image image;

    protected ImagePlus(Image image) {
        this.image = image;
    }

    protected ImagePlus(SingleMessage image) {
        if (!(image instanceof Image)) {
            throw new MiraiPlusException(999, "不支持的Image对象");
        }
        this.image = (Image) image;
    }

    public String getImageUrl() {
        MessageEvent event = MiraiPlusThreadLocal.messageEvent.get();
        if (event == null) {
            throw new MiraiPlusException(999, "当前非收到消息链路");
        }
        return event.getBot().queryImageUrl(this.image);
    }

}
