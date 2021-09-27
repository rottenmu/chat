package com.open.im.commons.entity;

import java.io.Serializable;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class BrokerInfo implements Serializable {
    private String host;
    private Integer port;
    private Integer webPort;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    @Override
    public String toString() {
        return "BrokerInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", webPort=" + webPort +
                '}';
    }
}
