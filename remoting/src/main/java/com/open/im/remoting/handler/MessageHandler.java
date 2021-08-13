package com.open.im.remoting.handler;

import com.open.im.remoting.netty.codec.Message;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public interface MessageHandler<M extends Message> {
    void execute(NioSocketChannel channel, M message);
    String getType();
}
