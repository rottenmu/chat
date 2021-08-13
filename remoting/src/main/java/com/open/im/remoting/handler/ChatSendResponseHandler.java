package com.open.im.remoting.handler;

import com.open.im.remoting.netty.codec.ChatSendResponse;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ChatSendResponseHandler implements MessageHandler<ChatSendResponse> {
    @Override
    public void execute(NioSocketChannel channel, ChatSendResponse message) {
        System.out.println(String.format("[execute][发送结果：%s]",message));
    }

    @Override
    public String getType() {
        return ChatSendResponse.TYPE;
    }
}
