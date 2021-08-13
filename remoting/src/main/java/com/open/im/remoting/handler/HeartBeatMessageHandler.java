package com.open.im.remoting.handler;

import com.open.im.remoting.context.NettyConnectionHolder;
import com.open.im.remoting.enums.RequestType;
import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.HeartbeatRequest;
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

public class HeartBeatMessageHandler implements MessageHandler<HeartbeatRequest> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(NioSocketChannel channel, HeartbeatRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
        channel.writeAndFlush(new DefaultMessage(RequestType.HEART_RESPONSE, heartbeatResponse));
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }
}
