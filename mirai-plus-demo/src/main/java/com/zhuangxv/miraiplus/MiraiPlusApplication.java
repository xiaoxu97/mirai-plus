package com.zhuangxv.miraiplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaoxu
 * @date 2020/10/15
 **/
@EnableMiraiPlus(scanBasePackages = {"com.zhuangxv.miraiplus.handler"})
@SpringBootApplication
public class MiraiPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiPlusApplication.class, args);
    }

}
