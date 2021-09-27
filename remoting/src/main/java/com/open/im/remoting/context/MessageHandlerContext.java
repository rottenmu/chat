package com.open.im.remoting.context;

import com.open.im.remoting.netty.codec.*;
import com.open.im.remoting.handler.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class MessageHandlerContext {
    private static ConcurrentMap<String, MessageHandler> handlers = new ConcurrentHashMap<String, MessageHandler>();

    private MessageHandlerContext(){}

    public static MessageHandler getMessageHandler(String type){
        MessageHandler messageHandler = handlers.get(type);
        if (messageHandler == null) {
            throw new IllegalArgumentException(String.format("类型(%s)找不到匹配的 MessageHandler处理器",type));
        }
        return messageHandler;
    }

    public static void setHandlers(ConcurrentMap<String, MessageHandler> handlerMap) {
       handlers.putAll(handlerMap);
    }

    /**
     * 获得 MessageHandler 处理的消息类
     *
     * @param handler 处理器
     * @return 消息类
     */
    public static Class<? extends Message> getMessageClass(MessageHandler handler) {
        Class<?> targetClass = handler.getClass();
        // 获得接口的 Type 数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        // 此处，是以父类的接口为准
        while (0 == interfaces.length && Objects.nonNull(superclass)) {
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        // 遍历 interfaces 数组
        for (Type type : interfaces) {
            // 要求 type 是泛型参数
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                // 要求是 MessageHandler 接口
                if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    // 取首个元素
                    if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                        return (Class<Message>) actualTypeArguments[0];
                    } else {
                        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }

}
