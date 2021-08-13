package com.open.im.broker.route;

import com.open.im.remoting.handler.MessageHandler;
import com.open.im.remoting.netty.codec.ChatRedirectToUserRequest;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class ForwardMessageHandler implements MessageHandler<ChatRedirectToUserRequest> {

    @Override
    public void execute(NioSocketChannel channel, ChatRedirectToUserRequest message) {

    }

    @Override
    public String getType() {
        return null;
    }
}
