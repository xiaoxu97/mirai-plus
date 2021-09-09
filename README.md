# MIRAI-PLUS
## ※本项目基于mirai进行java二次开发，旨在简化java用户的开发。
## ※本项目已停止开发，新框架https://github.com/go-cqhttp/java
## 源地址https://github.com/mamoe/mirai
### 许可证

    Copyright (C) 2019-2020 Mamoe Technologies and contributors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

`mirai-plus` 采用 `AGPLv3` 协议开源。为了整个社区的良性发展，我们**强烈建议**您做到以下几点：

- **间接接触（包括但不限于使用 `Http API` 或 跨进程技术）到 `mirai-plus` 的软件使用 `AGPLv3` 开源**
- **不鼓励，不支持一切商业使用**

鉴于项目的特殊性，开发团队可能在任何时间**停止更新**或**删除项目**。
* ### 使用前
    * clone并执行mvn clean install。
    * 创建空的springboot项目并引用依赖。
    * 在启动类上加注解@EnableMiraiPlus。
    * 注意，由于mirai与springboot中kotlin版本依赖关系，需要你使用继承方式依赖mirai-plus
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
    * mirai-plus.tempPath 机器人的临时文件储存路径
    * 配置支持数组形式，以便支持多bot管理。
* ### 开始使用
    * 创建一个类并加入spring管理或在@EnableMiraiPlus注解中增加扫描包的路径。
    * 类上使用@MiraiPlusHandler注解，该注解有一个参数botName，用于区分多bot情况。
    * 对应方法加上@MiraiPlusHandler注解并加上需要解析的类型，如下：
        * @MiraiPlusGroupMessageHandler 监听群消息。
            * regex 匹配改正则消息时触发该事件
            * groupId 只有当收到消息的群号为该参数指定内容时，触发该事件，默认为0即不限制
            * senderId 只有当发言人为该参数指定id时，触发该事件，默认为0即不限制
            * isAt 是否被艾特，如果为true则被艾特的消息才会触发该事件，反之不会触发。
        * @MiraiPlusFriendMessageHandler 监听私聊消息。
            * regex 匹配改正则消息时触发该事件
            * senderId 只有当发言人为该参数指定id时，触发该事件，默认为0即不限制
        * @MiraiPlusTempMessageHandler 监听临时会话。
            * regex 匹配改正则消息时触发该事件
            * groupId 只有当临时会话从该参数指定群聊发起时，触发该事件，默认为0即不限制
            * senderId 只有当发言人为该参数指定id时，触发该事件，默认为0即不限制
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
