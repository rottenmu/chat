package com.open.im.remoting.netty;

import com.open.im.remoting.AbstractNettyRemoting;
import com.open.im.remoting.RemotingService;
import com.open.im.remoting.dispather.MessageDispatcher;
import com.open.im.remoting.enums.RequestType;
import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.HeartbeatRequest;
import com.open.im.remoting.netty.codec.MessageDecoder;
import com.open.im.remoting.netty.codec.MessageEncoder;
import com.open.im.remoting.utils.RemotingUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

public class NettyRemotingClient extends AbstractNettyRemoting implements RemotingService {
    private final Logger logger = LoggerFactory.getLogger(NettyRemotingClient.class.getName());

    private  volatile Channel channel;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private NettyClientHandler nettyClientHandler;
    private final Integer RECONNECT_SECONDS = 3;
    private String host;
    private int port;

    public NettyRemotingClient(String host, int port){
        this.host = host;
        this.port = port;

        start();
    }

    private void prepareSharableHandlers(){
        nettyClientHandler = new NettyClientHandler();
    }

    @Override
    public void start() {
        prepareSharableHandlers();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.SO_KEEPALIVE, true)
                 .option(ChannelOption.TCP_NODELAY, true)
                 .handler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IdleStateHandler(0,30, 0 ,TimeUnit.SECONDS))
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new MessageDispatcher())
                                .addLast(nettyClientHandler);
                    }
                });


        try {
            bootstrap.connect(this.host, this.port).sync().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future){
                        if (!future.isSuccess()) {
                            logger.error("[start netty client 服务器[{}:{}]失败]",host, port);
                            reConnect();
                            return;
                        }
                        channel = future.channel();
                        logger.info("[start][netty client 服务器 [{}:{}]] 连接成功",host, port);
                    }
                });
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e.fillInStackTrace());
        }
    }

    private void reConnect(){
        eventLoopGroup.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("[reconnect][开始重连]");
                try {
                    start();
                } catch (Exception e) {
                    logger.error("[reconnect][重连失败]", e);
                }
            }
        },RECONNECT_SECONDS, TimeUnit.SECONDS);

        logger.info("[reconnect][{} 秒后将发起重连]", RECONNECT_SECONDS);
    }

    @Override
    public void shutdown() {
        channel.close();
        eventLoopGroup.shutdownGracefully();
    }

    @ChannelHandler.Sharable
    class NettyClientHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            reConnect();
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                if (IdleState.WRITER_IDLE.equals(idleStateEvent.state())){
                    String remoteAddr = RemotingUtils.parseChannelRemoteAddr(ctx.channel());
                    logger.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddr);
                    HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
                    heartbeatRequest.setClientId(System.getProperty("user"));
                    heartbeatRequest.setChannelId(ctx.channel().id());
                    heartbeatRequest.setRemoteAddr(remoteAddr);
                    ctx.writeAndFlush(new DefaultMessage(RequestType.HEART_REQUEST, heartbeatRequest))
                            .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                }
            }
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("[NettyRemotingClient.exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
            ctx.channel().close();
        }
    }

    public void sendMessage(DefaultMessage defaultMessage){
        ChannelFuture future = channel.writeAndFlush(defaultMessage);
        future.addListener((ChannelFutureListener) channelFuture ->
                logger.info("客户端手动发消息成功={}", defaultMessage.toString()));
    }

}
