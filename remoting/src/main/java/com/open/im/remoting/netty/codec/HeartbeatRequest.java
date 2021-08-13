package com.open.im.remoting.netty.codec;


import com.open.im.remoting.enums.RequestType;
import io.netty.channel.ChannelId;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class HeartbeatRequest implements Message {
    public static final String TYPE = RequestType.HEART_REQUEST.name();

    private String hostname;
    private Integer port;
    private ChannelId channelId;
    private String remoteAddr;
    private String clientId;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public String toString() {
        return "HeartbeatRequest{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                ", channelId=" + channelId +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
