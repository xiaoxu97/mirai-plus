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
    * MessagePlus:
        * at 增加艾特指定qq。
        * at 增加艾特指定会员，MemberPlus可通过GroupPlus.getMemberPlus获取。
        * atAll 增加艾特全体成员。
        * text 增加普通文本消息。
        * face 增加QQ内置表情，通过枚举类FacePlus构建
        * image 增加自定义图片，参数支持url文本，File对象，InputStream。
        * flashImage 增加闪图，参数支持url文本，File对象，InputStream。
        * getAt 获取消息体中所有的艾特对象。
        * getImage 获取消息体中所有的图片对象。
    * 各个组件可进行的操作
        * BotPlus 机器人对象
            * getFriends 获取机器人的好友列表。
            * getFriend 获取机器人的指定好友。
            * getGroups 获取机器人所在的群列表。
            * getGroup 获取机器人所在的指定群。
        * FriendPlus 好友对象
            * getQQ 获取该好友的QQ号。
            * getAvatarUrl 获取该好友的头像图片地址。
            * getNick 获取该好友的昵称。
            * sendMessage 给该好友发送消息。
        * GroupPlus 群对象
            * getId 获取群号。
            * getName 获取群名称。
            * getAvatarUrl 获取群头像地址。
            * getOwner 获取群主。
            * sendMessage 发送消息。
            * getMemberPlus 获取指定群成员。
            * getMemberPlusList 获取全部群成员。
        * MemberPlus 群成员对象
            * getQQ 获取该成员QQ号。
            * getNameCard 获取该成员群名片。
            * getPermission 获取该成员权限 0代表一般成员 1代表管理员 2代表群主。
            * getSpecialTitle 获取该成员头衔。
            * getMuteTimeRemaining 获取该成员被禁言剩余时长。
            * mute 设置该成员禁言。
            * unMute 解除该成员禁言。
            * kick 将该成员提出群聊（可以附加消息）。
            * sendMessage 向该成员发送消息，若群成员同时是好友, 则会发送好友消息. 否则发送临时会话消息。
        * AtPlus 艾特对象
            * getMemberPlus 获取被艾特的群成员对象。
        * ImagePlus 图片对象
            * getImageUrl 获取该图片的url地址。