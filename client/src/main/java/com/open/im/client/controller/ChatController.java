package com.open.im.client.controller;
import com.open.im.remoting.context.NettyConnectionHolder;
import com.open.im.remoting.netty.NettyRemotingClient;
import com.open.im.remoting.netty.codec.DefaultMessage;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@RestController
public class ChatController {

    @Autowired
    private NettyRemotingClient nettyRemotingClient;

    @PostMapping(value = "/login")
    public String login( @RequestBody DefaultMessage defaultMessage){
        nettyRemotingClient.sendMessage(defaultMessage);
//        NioSocketChannel nioSocketChannel = NettyConnectionHolder.getNioSocketChannel(user);
//        nioSocketChannel.writeAndFlush(defaultMessage);
      //  NettyConnectionHolder.send(user, defaultMessage);
        return "SUCCESS";
    }

    @PostMapping(value = "/send/{user}")
    public String send(@PathVariable String user, @RequestBody DefaultMessage defaultMessage){
        nettyRemotingClient.sendMessage(defaultMessage);
        return "SUCCESS";
    }

}
