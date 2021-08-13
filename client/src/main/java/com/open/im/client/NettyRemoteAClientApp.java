package com.open.im.client;

import com.open.im.remoting.netty.NettyRemotingClient;
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
public class NettyRemoteAClientApp {

    public static void main(String[] args) {
        NettyRemotingClient nettyRemotingClient = new NettyRemotingClient();
        nettyRemotingClient.start();
        System.setProperty("user","a");
        System.setProperty("spring.profiles.active", "aclient");
        SpringApplication.run(NettyRemoteAClientApp.class, args);
    }
}
