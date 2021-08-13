//package com.open.im.broker.namesrv;
//
//import com.open.im.commons.codec.Invocation;
//import com.open.im.commons.codec.InvocationDecoder;
//import com.open.im.commons.codec.InvocationEncoder;
//import com.open.im.commons.dispather.MessageDispatcher;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.handler.timeout.IdleStateHandler;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * <p>Title: OpenIm</p>
// * <p>Description: OpenIm</p>
// * <p>Copyright: Copyright (c) 2017-2050</p>
// *
// * @author rotten
// * @version 1.0
// */
//
//public class NameSrvClient {
//    private final Logger logger = LoggerFactory.getLogger(NameSrvClient.class.getName());
//
//    private volatile Channel channel;
//    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//    private final String HOST = "127.0.0.1";
//    private final int PORT = 13003;
//    private NamesrvClientInitializer namesrvClientInitializer;
//
//    private void prepareSharedHandles(){
//        this.namesrvClientInitializer = new NamesrvClientInitializer();
//    }
//
//    public void start(){
//        prepareSharedHandles();
//
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(eventLoopGroup)
//                .channel(NioSocketChannel.class)
//                .remoteAddress(HOST, PORT)
//                .option(ChannelOption.SO_KEEPALIVE ,true)
//                .option(ChannelOption.TCP_NODELAY, true)
//                .handler(namesrvClientInitializer);
//
//        bootstrap.connect().addListeners(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                if (!future.isSuccess()) {
//                    logger.error("[start netty client 服务器[{}:{}]失败]",HOST, PORT);
//                    reConnect();
//                    return;
//                }
//
//                channel = future.channel();
//
//                logger.info("[start][netty client 服务器 [{}:{}]] 连接成功",HOST, PORT);
//            }
//        });
//    }
//
//    private void send(Invocation invocation){
//        if (channel == null) {
//            logger.error("[send][连接不存在]");
//            return;
//        }
//
//        if (!channel.isActive()) {
//            logger.error("[send][连接[{}] 未激活]", channel.id());
//            return;
//        }
//
//        channel.writeAndFlush(invocation);
//    }
//
//    private void reConnect(){
//        this.eventLoopGroup.schedule(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("[reconnect][开始重连]");
//
//                try {
//                    start();
//                } catch (Exception e) {
//                    logger.error("[reconnect][重连失败]", e);
//                }
//            }
//        },20, TimeUnit.SECONDS);
//
//        logger.info("[reconnect][{} 秒后将发起重连]", 3);
//    }
//
//    private void shutdown(){
//        if (channel != null) {
//            channel.close();
//        }
//
//        this.eventLoopGroup.shutdownGracefully();
//    }
//
//    @ChannelHandler.Sharable
//    static class NamesrvClientInitializer extends ChannelInitializer<Channel>{
//        @Override
//        protected void initChannel(Channel ch) throws Exception {
//            ch.pipeline()
//                    .addLast(new IdleStateHandler(5, 0,0))
//                    .addLast(new ReadTimeoutHandler(3 * 5, TimeUnit.SECONDS))
//                    .addLast(new InvocationEncoder())
//                    .addLast(new InvocationDecoder())
//                    .addLast(new NamesrvClientHandler());
//        }
//    }
//
//    @ChannelHandler.Sharable
//    static class NamesrvClientHandler extends ChannelInboundHandlerAdapter{
//        private final Logger logger = LoggerFactory.getLogger(NamesrvClientHandler.class.getName());
//
//        private NameSrvClient nameSrvClient;
//
//        public NamesrvClientHandler(){
//            initClient();
//        }
//
//        private void initClient(){
//            this.nameSrvClient = new NameSrvClient();
//        }
//
//        @Override
//        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//            if (!ctx.channel().isActive()) {
//                nameSrvClient.reConnect();
//            }
//            super.channelInactive(ctx);
//        }
//
//        @Override
//        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//            if (evt instanceof IdleStateEvent){
//                logger.info("[userEventTriggered][发起一次心跳]");
//                NameSrvHeartbeatRequest heartbeatRequest = new NameSrvHeartbeatRequest();
//                heartbeatRequest.setPort(2002);
//                heartbeatRequest.setHost("127.0.0.1");
//                ctx.writeAndFlush(new Invocation(NameSrvHeartbeatRequest.TYPE, heartbeatRequest))
//                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//            }
//            super.userEventTriggered(ctx, evt);
//        }
//
//        @Override
//        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//            logger.error("[NameSrvClient.exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
//            super.exceptionCaught(ctx, cause);
//        }
//    }
//}