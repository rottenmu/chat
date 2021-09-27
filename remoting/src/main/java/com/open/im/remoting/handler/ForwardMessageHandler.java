package com.open.im.remoting.handler;

import com.open.im.remoting.netty.codec.ForwardMessageRequest;
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

public class ForwardMessageHandler implements MessageHandler<ForwardMessageRequest> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(NioSocketChannel channel, ForwardMessageRequest message) {
        logger.info("[execute][收到消息：{}]", message);
    }

    @Override
    public String getType() {
        return ForwardMessageRequest.TYPE;
    }
}
