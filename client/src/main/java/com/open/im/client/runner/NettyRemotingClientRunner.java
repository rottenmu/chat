package com.open.im.client.runner;

import com.open.im.remoting.netty.NettyRemotingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Component
public class NettyRemotingClientRunner implements ApplicationRunner {
    @Autowired
    private NettyRemotingClient nettyRemotingClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyRemotingClient.start();
    }
}
