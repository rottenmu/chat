package com.open.im.broker;

import com.open.im.remoting.config.NettyServerConfig;
import com.open.im.remoting.netty.NettyRemotingServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@SpringBootApplication
public class NettyRemoteBServerApp {
    public static void main(String[] args) {

        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        nettyServerConfig.setHostname("127.0.0.1");
        nettyServerConfig.setPort(12002);
        NettyRemotingServer nettyRemotingServer = new NettyRemotingServer(nettyServerConfig);
        nettyRemotingServer.start();

        System.setProperty("spring.profiles.active", "bserver");
        SpringApplication.run(NettyRemoteBServerApp.class, args);
    }
}
