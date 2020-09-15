# MIRAI-PLUS
* ### 使用前
    * clone并执行mvn clean install。
    * 创建空的springboot项目并引用依赖。
    * 在启动类上加注解@EnableMiraiPlus。
    * 注意，由于kotlin版本依赖关系，需要你使用继承方式依赖mirai-plus
        ```
        <parent>
            <groupId>com.zhuangxv</groupId>
            <artifactId>mirai-plus-pom</artifactId>
            <version>1.0-SNAPSHOT</version>
        </parent>
        ```
        或者不以继承方式，但要排除依赖并指定版本。
        ```
        <dependency>
            <groupId>com.zhuangxv</groupId>
            <artifactId>mirai-plus</artifactId>
            <version>1.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <artifactId>kotlin-stdlib</artifactId>
                    <groupId>org.jetbrains.kotlin</groupId>
                </exclusion>
                <exclusion>
                    <artifactId>kotlin-stdlib-common</artifactId>
                    <groupId>org.jetbrains.kotlin</groupId>
                </exclusion>
                <exclusion>
                    <artifactId>kotlin-stdlib-jdk7</artifactId>
                    <groupId>org.jetbrains.kotlin</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib-common</artifactId>
            <version>1.4.0</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib</artifactId>
            <version>1.4.0</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-stdlib-jdk7</artifactId>
            <version>1.4.0</version>
            <exclusions>
                <exclusion>
                    <artifactId>kotlin-stdlib</artifactId>
                    <groupId>org.jetbrains.kotlin</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        ```
* ### 配置
    * mirai-plus.botName 机器人名字，多个机器人时用于区分。
    * mirai-plus.botQQ 机器人QQ。
    * mirai-plus.botPassword 机器人密码。
    * mirai-plus.deviceInfoPath 设备文件保存路径，需要每个机器人独立的路径。
* ### 开始使用
    * 创建一个类并加入spring管理或在@EnableMiraiPlus注解中增加扫描包的路径。
    * 类上使用@MiraiPlusHandler注解。
    * 对应方法加上@MiraiPlusHandler注解并加上需要解析的类型，如下：
        * @MiraiPlusGroupMessageHandler 监听群消息。
        * @MiraiPlusFriendMessageHandler 监听私聊消息。
        * @MiraiPlusTempMessageHandler 监听临时会话。
        * 待补充。
    * 方法支持的参数列表：
        * BotPlus 将注入收到消息的机器人实例。
        * GroupPlus 如果是群消息，会注入群对应实例，否则注入null。
        * String 消息内容。
        * MessagePlus 消息体。
        * FriendPlus 如果是私聊消息，会注入发送人(好友)对应实例，否则注入null。
        * MemberPlus 如果是群消息，会注入发送人(群成员)对应实例，否则注入null。
        * UserPlus 会注入发送人对应实例，否则注入null。
    * 方法支持的返回值列表：
        * void 什么也不做。
        * MessagePlus 回复对应消息。
    * 构建MessagePlus:
        * .at 增加艾特指定qq。
        * .at 增加艾特指定会员，MemberPlus可通过GroupPlus.getMemberPlus获取。
        * .atAll 增加艾特全体成员。
        * .text 增加普通文本消息。
        * .face 增加QQ内置表情，通过枚举类FacePlus构建
        * .image 增加自定义图片，参数支持url文本，File对象，InputStream。
        * .flashImage 增加闪图，参数支持url文本，File对象，InputStream。