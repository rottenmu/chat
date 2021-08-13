package com.open.im.client.config;

import com.open.im.commons.spring.SpringContextUtils;
import com.open.im.remoting.netty.NettyRemotingClient;
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
public class ClientConfig {

    @Bean
    public SpringContextUtils springContextUtils(){
        return new SpringContextUtils();
    }

    @Bean
    public NettyRemotingClient nettyRemotingClient(){
        NettyRemotingClient nettyRemotingClient = new NettyRemotingClient();
        nettyRemotingClient.start();
        return nettyRemotingClient;
    }
}
