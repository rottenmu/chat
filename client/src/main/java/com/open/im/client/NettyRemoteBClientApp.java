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
public class NettyRemoteBClientApp {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "bclient");
        SpringApplication.run(NettyRemoteBClientApp.class, args);
    }

}
