package com.open.im.remoting.dispather;

import com.alibaba.fastjson.JSON;
import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.Message;
import com.open.im.remoting.handler.MessageHandler;
import com.open.im.remoting.context.MessageHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<DefaultMessage> {
    private final ExecutorService executor =  Executors.newFixedThreadPool(4);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultMessage msg) {
        MessageHandler messageHandler = MessageHandlerContext.getMessageHandler(msg.getType());
        Class<? extends Message> messageClass = MessageHandlerContext.getMessageClass(messageHandler);
        Message message = JSON.parseObject(msg.getMessage(), messageClass);
        messageHandler.execute(((NioSocketChannel) ctx.channel()), message);
//        executor.submit(() ->{
//            messageHandler.execute(ctx.channel(), message);
//        });
    }
}
