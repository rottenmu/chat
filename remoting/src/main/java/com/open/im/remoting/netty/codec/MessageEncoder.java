package com.open.im.remoting.netty.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@ChannelHandler.Sharable
public class MessageEncoder extends MessageToByteEncoder<DefaultMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, DefaultMessage msg, ByteBuf out) throws Exception {
        byte[] message = JSON.toJSONBytes(msg);
        out.writeInt(message.length);
        out.writeBytes(message);
        System.out.format("[encode][连接(%s) 编码了一条消息(%s)]", ctx.channel().id(), msg.toString());
    }
}
