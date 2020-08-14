package com.zhuangxv.miraiplus.component;

import com.zhuangxv.miraiplus.annotation.MiraiPlusFriendMessageHandler;
import com.zhuangxv.miraiplus.annotation.MiraiPlusGroupMessageHandler;
import com.zhuangxv.miraiplus.annotation.MiraiPlusHandler;
import com.zhuangxv.miraiplus.annotation.MiraiPlusTempMessageHandler;
import com.zhuangxv.miraiplus.exception.MiraiPlusException;
import com.zhuangxv.miraiplus.injector.ObjectInjector;
import com.zhuangxv.miraiplus.pojo.HandlerMethod;
import com.zhuangxv.miraiplus.util.MiraiPlusApplicationBeanContext;
import kotlin.coroutines.CoroutineContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoxu
 * @date 2020-08-07 16:09
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MiraiPlusMessageDispatcher extends SimpleListenerHost {
    private final List<ObjectInjector<?>> objectInjectorList;

    private final Map<String, List<HandlerMethod>> handlerMethodMap = new HashMap<>();
    private final Map<Class<?>, ObjectInjector<?>> objectInjectorMap = new HashMap<>();

    public void init() {
        Map<String, Object> handlers = MiraiPlusApplicationBeanContext.getBeansWithAnnotation(MiraiPlusHandler.class);
        handlers.forEach((name, object) -> {
            Class<?> aClass = object.getClass();
            MiraiPlusHandler miraiPlusHandlerClass = aClass.getAnnotation(MiraiPlusHandler.class);
            Set<Method> methodSet = Arrays.stream(aClass.getMethods()).filter(method -> method.isAnnotationPresent(MiraiPlusHandler.class)).collect(Collectors.toSet());
            methodSet.forEach(method -> {
                HandlerMethod handlerMethod = new HandlerMethod() {
                    {
                        setType(aClass);
                        setMethod(method);
                        setObject(object);
                    }
                };
                MiraiPlusHandler miraiPlusHandlerMethod = method.getAnnotation(MiraiPlusHandler.class);
                if ("none".equals(miraiPlusHandlerMethod.botName()) && "none".equals(miraiPlusHandlerClass.botName())) {
                    for (BotPlus botPlus : BotPlusFactory.getBotPlus()) {
                        this.handlerMethodMap.computeIfAbsent(botPlus.getBotName(), k -> new ArrayList<>()).add(handlerMethod);
                    }
                    return;
                }
                if (!"none".equals(miraiPlusHandlerMethod.botName())) {
                    this.handlerMethodMap.computeIfAbsent(miraiPlusHandlerMethod.botName(), k -> new ArrayList<>()).add(handlerMethod);
                    return;
                }
                this.handlerMethodMap.computeIfAbsent(miraiPlusHandlerClass.botName(), k -> new ArrayList<>()).add(handlerMethod);
            });
        });
        objectInjectorList.forEach(objectInjector -> objectInjectorMap.put(objectInjector.getType(), objectInjector));
    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        throw new MiraiPlusException(999, exception.getMessage(), "{}", exception);
    }

    @EventHandler
    public ListeningStatus handle(MessageEvent event) {
        BotPlus botPlus = BotPlusFactory.getBotPlus(event.getBot().getId());
        if (botPlus == null) {
            return ListeningStatus.LISTENING;
        }
        List<HandlerMethod> handlerMethodList = this.handlerMethodMap.get(botPlus.getBotName());
        if (handlerMethodList == null || handlerMethodList.isEmpty()) {
            return ListeningStatus.LISTENING;
        }
        MiraiPlusThreadLocal.messageEvent.set(event);
        try {
            if (event instanceof GroupMessageEvent) {
                this.handleGroupMessage(handlerMethodList, (GroupMessageEvent) event);
            } else if (event instanceof FriendMessageEvent) {
                this.handleFriendMessage(handlerMethodList, (FriendMessageEvent) event);
            } else if (event instanceof TempMessageEvent) {
                this.handleTempMessage(handlerMethodList, (TempMessageEvent) event);
            }
        } finally {
            MiraiPlusThreadLocal.messageEvent.remove();
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 处理群聊消息
     * @param handlerMethodList 处理方法列表
     * @param event 消息事件
     */
    private void handleGroupMessage(List<HandlerMethod> handlerMethodList, GroupMessageEvent event) {
        Set<HandlerMethod> handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
            if (!handlerMethod.getMethod().isAnnotationPresent(MiraiPlusGroupMessageHandler.class)) {
                return false;
            }
            MiraiPlusGroupMessageHandler miraiPlusGroupMessageHandler = handlerMethod.getMethod().getAnnotation(MiraiPlusGroupMessageHandler.class);
            if (miraiPlusGroupMessageHandler.groupId() != 0 && miraiPlusGroupMessageHandler.groupId() != event.getGroup().getId()) {
                return false;
            }
            if (miraiPlusGroupMessageHandler.senderId() != 0 && miraiPlusGroupMessageHandler.senderId() != event.getSender().getId()) {
                return false;
            }
            return miraiPlusGroupMessageHandler.regex().equals("none") || event.getMessage().contentToString().matches(miraiPlusGroupMessageHandler.regex());
        }).collect(Collectors.toSet());
        this.handleMethod(handlerMethodSet, event.getGroup());
    }

    /**
     * 处理私聊消息
     * @param handlerMethodList 处理方法列表
     * @param event 消息事件
     */
    private void handleFriendMessage(List<HandlerMethod> handlerMethodList, FriendMessageEvent event) {
        Set<HandlerMethod> handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
            if (!handlerMethod.getMethod().isAnnotationPresent(MiraiPlusFriendMessageHandler.class)) {
                return false;
            }
            MiraiPlusFriendMessageHandler miraiPlusFriendMessageHandler = handlerMethod.getMethod().getAnnotation(MiraiPlusFriendMessageHandler.class);

            if (miraiPlusFriendMessageHandler.senderId() != 0 && miraiPlusFriendMessageHandler.senderId() != event.getSender().getId()) {
                return false;
            }
            return miraiPlusFriendMessageHandler.regex().equals("none") || event.getMessage().contentToString().matches(miraiPlusFriendMessageHandler.regex());
        }).collect(Collectors.toSet());
        this.handleMethod(handlerMethodSet, event.getSender());
    }

    /**
     * 处理临时会话
     * @param handlerMethodList 处理方法列表
     * @param event 消息事件
     */
    private void handleTempMessage(List<HandlerMethod> handlerMethodList, TempMessageEvent event) {
        Set<HandlerMethod> handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
            if (!handlerMethod.getMethod().isAnnotationPresent(MiraiPlusTempMessageHandler.class)) {
                return false;
            }
            MiraiPlusTempMessageHandler miraiPlusTempMessageHandler = handlerMethod.getMethod().getAnnotation(MiraiPlusTempMessageHandler.class);
            if (miraiPlusTempMessageHandler.groupId() != 0 && miraiPlusTempMessageHandler.groupId() != event.getGroup().getId()) {
                return false;
            }
            if (miraiPlusTempMessageHandler.senderId() != 0 && miraiPlusTempMessageHandler.senderId() != event.getSender().getId()) {
                return false;
            }
            return miraiPlusTempMessageHandler.regex().equals("none") || event.getMessage().contentToString().matches(miraiPlusTempMessageHandler.regex());
        }).collect(Collectors.toSet());
        this.handleMethod(handlerMethodSet, event.getSender());
    }

    private void handleMethod(Set<HandlerMethod> handlerMethodSet, Contact contact) {
        handlerMethodSet.forEach(handlerMethod -> {
            Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();
            Object[] objects = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                ObjectInjector<?> objectInjector = objectInjectorMap.get(parameterType);
                if (objectInjector == null) {
                    objects[i] = null;
                } else {
                    objects[i] = objectInjector.getObject();
                }
            }
            try {
                Object result = handlerMethod.getMethod().invoke(handlerMethod.getObject(), objects);
                if (result != null) {
                    if (result instanceof MessagePlus) {
                        contact.sendMessage(((MessagePlus) result).getMessageChain());
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

}