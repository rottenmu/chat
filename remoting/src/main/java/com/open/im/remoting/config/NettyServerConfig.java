package com.open.im.remoting.config;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class NettyServerConfig implements Cloneable{
    private int port = 3010;
    private String hostname;
    private int allIdleTimeSeconds;

    public NettyServerConfig(){}

    public NettyServerConfig(int port, String hostname) {
        this.port = port;
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
        this.allIdleTimeSeconds = allIdleTimeSeconds;
    }

    public int getAllIdleTimeSeconds() {
        return allIdleTimeSeconds;
    }

    @Override
    public String toString() {
        return "NettyServerConfig{" +
                "port=" + port +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
