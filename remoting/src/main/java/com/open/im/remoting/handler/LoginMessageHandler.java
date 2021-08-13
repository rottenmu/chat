package com.open.im.remoting.handler;

import com.open.im.remoting.context.NettyConnectionHolder;
import com.open.im.remoting.netty.codec.LoginMessage;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class LoginMessageHandler implements MessageHandler<LoginMessage> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(NioSocketChannel channel, LoginMessage message) {
        logger.info("[execute][用户({}) 登录]", message.toString());
        NettyConnectionHolder.addUser(message.getUserId(), channel);
    }

    @Override
    public String getType() {
        return LoginMessage.TYPE;
    }
}
