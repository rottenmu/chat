//package com.open.im.commons;
//
//import com.open.im.commons.codec.Message;
//import com.open.im.commons.handler.MessageHandler;
//import org.springframework.aop.framework.AopProxyUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * <p>Title: OpenIm</p>
// * <p>Description: OpenIm</p>
// * <p>Copyright: Copyright (c) 2017-2050</p>
// *
// * @author rotten
// * @version 1.0
// */
//
//public class MessageHandlerInitBeanProcessor implements InitializingBean, ApplicationContextAware {
//    private ApplicationContext applicationContext;
//    private volatile ConcurrentMap<String, MessageHandler> handlers = new ConcurrentHashMap<String, MessageHandler>();
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//       applicationContext.getBeansOfType(MessageHandler.class).values()
//                .forEach(handler ->{handlers.put(handler.getType(), handler);
//                    System.out.format("[afterPropertiesSet][消息处理器名称[%s]]",handler.getClass().getName()+"\n");
//                });
//      System.out.format("[afterPropertiesSet][消息处理器数量[%s]]",handlers.size());
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    public MessageHandler getMessageHandler(String type){
//        MessageHandler messageHandler = handlers.get(type);
//        if (messageHandler == null) {
//            throw new IllegalArgumentException(String.format("类型(%s)找不到匹配的 MessageHandler处理器",type));
//        }
//        return messageHandler;
//    }
//
//    public static Class<? extends Message> getMessageClass(MessageHandler handler){
//        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
//        Type[] interfaces = targetClass.getGenericInterfaces();
//        Class<?> superclass = targetClass.getSuperclass();
//        while ((Objects.isNull(interfaces) || interfaces.length ==0) && Objects.nonNull(superclass)){
//            interfaces = superclass.getGenericInterfaces();
//            superclass = targetClass.getSuperclass();
//        }
//
//        if (Objects.nonNull(interfaces)) {
//            for(Type type: interfaces){
//                if (type instanceof ParameterizedType){
//                    ParameterizedType parameterizedType = (ParameterizedType) type;
//                    if (MessageHandler.class.equals(parameterizedType.getRawType())) {
//                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length >0) {
//                            return (Class<Message>)actualTypeArguments[0];
//                        }else {
//                            throw new IllegalArgumentException(String.format("类型(%s)获得不到消息类型",handler));
//                        }
//                    }
//                }
//            }
//        }
//        throw new IllegalArgumentException(String.format("类型(%s)获得不到消息类型",handler));
//    }
//}
