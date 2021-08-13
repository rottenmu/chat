package com.open.im.remoting;

import com.open.im.remoting.handler.*;
import com.open.im.remoting.netty.codec.*;
import com.open.im.remoting.context.MessageHandlerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

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

public abstract class AbstractNettyRemoting {

    public AbstractNettyRemoting(){
        ConcurrentMap<String, MessageHandler> handlers = new ConcurrentHashMap<>();
        handlers.put(HeartbeatRequest.TYPE, new HeartBeatMessageHandler());
        handlers.put(HeartbeatResponse.TYPE, new HeartbeatResponseHandler());
        handlers.put(OneToOneMessage.TYPE,  new OneToOneSendMessageHandler());
        handlers.put(ChatSendResponse.TYPE, new ChatSendResponseHandler());
        handlers.put(LoginMessage.TYPE, new LoginMessageHandler());
        MessageHandlerContext.setHandlers(handlers);
    }

    protected void processRequest(final ChannelHandlerContext ctx, DefaultMessage message){
        MessageHandler messageHandler = MessageHandlerContext.getMessageHandler(message.getType());
        messageHandler.execute(((NioSocketChannel) ctx.channel()), message);
    }
}
