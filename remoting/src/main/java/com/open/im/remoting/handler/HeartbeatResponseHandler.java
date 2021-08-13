package com.open.im.remoting.handler;

import com.open.im.remoting.netty.codec.HeartbeatResponse;
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

public class HeartbeatResponseHandler implements MessageHandler<HeartbeatResponse> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(NioSocketChannel channel, HeartbeatResponse message) {
        logger.info("[execute][收到连接({}) 的心跳响应]", channel.id());
    }

    @Override
    public String getType() {
        return HeartbeatResponse.TYPE;
    }

}
