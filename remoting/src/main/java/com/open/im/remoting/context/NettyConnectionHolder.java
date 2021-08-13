package com.open.im.remoting.context;

import com.open.im.remoting.netty.codec.DefaultMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class NettyConnectionHolder {
    private static final Logger logger = LoggerFactory.getLogger(NettyConnectionHolder.class.getName());

    /**
     * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");
    private static final ConcurrentMap<ChannelId, NioSocketChannel> CHANNELS = new ConcurrentHashMap<ChannelId, NioSocketChannel>();
    private static final ConcurrentMap<String, NioSocketChannel> USERCHANNELS = new ConcurrentHashMap<>();

    public static void addUser(String user, NioSocketChannel channel){
        if (isConnectedUser(user)) {
            return;
        }

        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        USERCHANNELS.put(user, channel);
        CHANNELS.put(channel.id(), channel);
        logger.info("[add channel,channelId....{}",channel.id());
    }

    public static boolean isConnectedUser(String user){
        if (MapUtils.isNotEmpty(USERCHANNELS) && USERCHANNELS.containsKey(user)) {
            return true;
        }
        return false;
    }

    public static void remove(NioSocketChannel channel){
        Channel exitsChannel = CHANNELS.get(channel.id());
        if (exitsChannel == null) {
            logger.error("[NettyConnectionHolder.remove][连接({}) 不存在]", channel.id());
            return;
        }

        CHANNELS.remove(channel.id());
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)){
            USERCHANNELS.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        logger.info("[remove][一个连接({})离开]", channel.id());
    }

    public static NioSocketChannel getNioSocketChannel(String user){
        return USERCHANNELS.get(user);
    }

    public static void send(String user, DefaultMessage message){
        Channel channel = USERCHANNELS.get(user);
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            logger.error("[send][连接({})未激活]", channel.id());
        }

        channel.writeAndFlush(message);
    }

    public static void sendAll(DefaultMessage message){
        for (Channel channel : CHANNELS.values()) {
            if (!channel.isActive()) {
                logger.error("[send][连接({})未激活]", channel.id());
            }
            channel.writeAndFlush(message);
        }
    }
}
