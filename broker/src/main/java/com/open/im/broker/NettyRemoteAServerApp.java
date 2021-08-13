package com.open.im.broker;

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
public class NettyRemoteAServerApp {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "aserver");
        SpringApplication.run(NettyRemoteAServerApp.class, args);
    }
}
