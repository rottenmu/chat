package com.open.im.remoting.netty.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        if (in.readableBytes() <= 4){
            return;
        }

        int length = in.readInt();
        if (length <0){
            throw new CorruptedFrameException("negative length:"+ length);
        }

        if (in.readableBytes() < length){
            in.markReaderIndex();
            return;
        }

       byte [] contents = new byte[length];
        in.readBytes(contents);
        Message message = JSON.parseObject(contents, DefaultMessage.class);
        out.add(message);
        System.out.format("[decoder][连接[%s] 解析到一条消息[%s]]",ctx.channel().id(),message.toString());
    }
}
