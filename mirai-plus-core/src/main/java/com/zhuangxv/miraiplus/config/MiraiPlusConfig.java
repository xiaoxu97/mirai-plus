package com.zhuangxv.miraiplus.config;

import lombok.Data;

@Data
public class MiraiPlusConfig {

    /**
     * 机器人名
     */
    private String botName;

    /**
     * 机器人QQ
     */
    private Long botQQ;

    /**
     * 机器人密码
     */
    private String botPassword;

    /**
     * 机器人设备信息
     */
    private String deviceInfoPath;

    /**
     * 心跳周期
     */
    private Long heartbeatPeriodMillis = 60 * 1000L;

    /**
     * 心跳超时时间
     */
    private Long heartbeatTimeoutMillis = 5 * 1000L;

    /**
     * 心跳失败后第一次重连前的等待时间
     */
    private Long firstReconnectDelayMillis = 5 * 1000L;

    /**
     * 重连的间隔时间
     */
    private Long reconnectPeriodMillis = 5 * 1000L;

    /**
     * 最多尝试多少次重连
     */
    private Integer reconnectionRetryTimes = Integer.MAX_VALUE;

}