package com.open.im.client.config;
import com.open.im.commons.entity.User;
import com.open.im.commons.spring.SpringContextUtils;
import com.open.im.remoting.netty.NettyRemotingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private ChatClientProperties chatClientProperties;

    @Bean
    public SpringContextUtils springContextUtils(){
        return new SpringContextUtils();
    }

    @Bean
    public NettyRemotingClient nettyRemotingClient(RestTemplate restTemplate){
        User user = new User();
        user.setId(chatClientProperties.getId());
        user.setPhone(chatClientProperties.getPhone());
        NettyRemotingClient remotingClient = new NettyRemotingClient("127.0.0.1", 12002);
        return remotingClient;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
