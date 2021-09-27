package com.open.im.broker.namesrv;

import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.MessageDecoder;
import com.open.im.remoting.netty.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class NamesrvController implements DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(NamesrvController.class.getName());

    private volatile Channel channel;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private final String HOST = "127.0.0.1";
    private final int PORT = 13003;
    private NamesrvInitializer namesrvInitializer;

    private void prepareSharedHandles(){
        this.namesrvInitializer = new NamesrvInitializer();
    }

    public void start(){
        prepareSharedHandles();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST, PORT)
                .option(ChannelOption.SO_KEEPALIVE ,true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(namesrvInitializer);

        bootstrap.connect().addListeners(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    logger.error("[start netty client 服务器[{}:{}]失败]",HOST, PORT);
                    reConnect();
                    return;
                }

                channel = future.channel();

                logger.info("[start][netty client 服务器 [{}:{}]] 连接成功",HOST, PORT);
            }
        });
    }

    private void reConnect(){
        this.eventLoopGroup.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("[reconnect][开始重连]");

                try {
                    start();
                } catch (Exception e) {
                    logger.error("[reconnect][重连失败]", e);
                }
            }
        },20, TimeUnit.SECONDS);

        logger.info("[reconnect][{} 秒后将发起重连]", 3);
    }

    @Override
    public void destroy() throws Exception {
        if (channel != null) {
            channel.close();
        }

        this.eventLoopGroup.shutdownGracefully();
    }

    @ChannelHandler.Sharable
    static class NamesrvInitializer extends ChannelInitializer<Channel>{
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline()
                    .addLast(new IdleStateHandler(30, 0,0))
                    .addLast(new ReadTimeoutHandler(3 * 30, TimeUnit.SECONDS))
                    .addLast(new MessageDecoder())
                    .addLast(new MessageEncoder())
                    .addLast(new NamesrvHandler());
        }
    }

    @ChannelHandler.Sharable
    static class NamesrvHandler extends ChannelInboundHandlerAdapter{
        private final Logger logger = LoggerFactory.getLogger(NamesrvHandler.class.getName());

        private NamesrvController nameSrvClient;

        public NamesrvHandler(){
            initClient();
        }

        private void initClient(){
            this.nameSrvClient = new NamesrvController();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            if (!ctx.channel().isActive()) {
                nameSrvClient.reConnect();
            }
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){
                logger.info("[userEventTriggered][发起一次心跳]");
                NameSrvHeartbeatRequest heartbeatRequest = new NameSrvHeartbeatRequest();
                heartbeatRequest.setPort(2002);
                heartbeatRequest.setHost("127.0.0.1");
//                ctx.writeAndFlush(new DefaultMessage(NameSrvHeartbeatRequest.TYPE, heartbeatRequest))
//                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("[NameSrvClient.exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
            super.exceptionCaught(ctx, cause);
        }
    }
}