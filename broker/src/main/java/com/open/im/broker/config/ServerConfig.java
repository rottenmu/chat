package com.open.im.broker.config;

import com.open.im.remoting.netty.NettyRemotingServer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "im")
public class ServerConfig {
    private Integer brokerId;
    private String host;
    private Integer tcpPort;
    private Integer httpPort;
    private String httUrl;

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public String getHttUrl() {
        return httUrl;
    }

    public void setHttUrl(String httUrl) {
        this.httUrl = httUrl;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "brokerId=" + brokerId +
                ", host='" + host + '\'' +
                ", tcpPort=" + tcpPort +
                ", httpPort=" + httpPort +
                ", httUrl='" + httUrl + '\'' +
                '}';
    }
}
