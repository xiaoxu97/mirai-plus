# MIRAI-PLUS
* ### 使用前
    * clone并执行mvn clean install。
    * 创建空的springboot项目并引用依赖。
    * 在启动类上加注解@EnableMiraiPlus。
* ### 配置
    * mirai.botName 机器人名字，多个机器人时用于区分。
    * mirai.botQQ 机器人QQ。
    * mirai.botPassword 机器人密码。
    * mirai.deviceInfoPath 设备文件保存路径，需要每个机器人独立的路径。
* ### 开始试用
    * 创建一个类并加入spring管理或在@EnableMiraiPlus注解中增加扫描包的路径。
    * 类上使用@MiraiPlusHandler注解。
    * 对应方法加上@MiraiPlusHandler注解并加上需要解析的类型，如下：
        * @MiraiPlusGroupMessageHandler 监听群消息。
        * 待补充。
    * 方法支持的参数列表：
        * BotPlus 将注入收到消息的机器人实例。
        * GroupPlus 如果是群消息，会注入群对应实例，否则注入null。
        * String 消息内容。
        * FriendPlus 如果是私聊消息，会注入发送人(好友)对应实例，否则注入null。
        * MemberPlus 如果是群消息，会注入发送人(群成员)对应实例，否则注入null。
        * UserPlus 会注入发送人对应实例，否则注入null。
    * 方法支持的返回值列表：
        * void 什么也不做。
        * MessagePlus 回复对应消息。