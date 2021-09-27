package com.open.im.remoting.netty;

import com.open.im.remoting.AbstractNettyRemoting;
import com.open.im.remoting.RemotingService;
import com.open.im.remoting.context.NettyConnectionHolder;
import com.open.im.remoting.dispather.MessageDispatcher;
import com.open.im.remoting.netty.codec.DefaultMessage;
import com.open.im.remoting.netty.codec.MessageDecoder;
import com.open.im.remoting.netty.codec.MessageEncoder;
import com.open.im.remoting.config.NettyServerConfig;
import com.open.im.remoting.utils.RemotingUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>`
 *
 * @author rotten
 * @version 1.0
 */

public class NettyRemotingServer extends AbstractNettyRemoting implements RemotingService{
    private final Logger logger = LoggerFactory.getLogger(NettyRemotingClient.class.getName());

    private EventLoopGroup eventLoopGroupBoss;
    private EventLoopGroup eventLoopGroupWorker;
    private ServerBootstrap serverBootstrap;
    private volatile Channel channel;
    private NettyServerConfig nettyServerConfig;
    private NettyServerHandler nettyServerHandler;

    public NettyRemotingServer(final NettyServerConfig nettyServerConfig){
        this.nettyServerConfig = nettyServerConfig;
        this.serverBootstrap = new ServerBootstrap();
        this.eventLoopGroupBoss = new NioEventLoopGroup();
        this.eventLoopGroupWorker = new NioEventLoopGroup();
    }

    private void prepareSharableHandles(){
        this.nettyServerHandler = new NettyServerHandler();
    }

    @Override
    public void start() {
        prepareSharableHandles();

        this.serverBootstrap.group(eventLoopGroupBoss, eventLoopGroupWorker)
                .channel(NioServerSocketChannel.class)
                .localAddress(nettyServerConfig.getHostname(), nettyServerConfig.getPort())
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IdleStateHandler(31, 0, 0, TimeUnit.SECONDS))
                                .addLast(new MessageDecoder())
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDispatcher())
                                .addLast(nettyServerHandler);
                    }
                });
        try {
            ChannelFuture future = this.serverBootstrap.bind().sync();
            channel = future.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        }

    }

    @Override
    public void shutdown() {
        this.eventLoopGroupWorker.shutdownGracefully();
        this.eventLoopGroupBoss.shutdownGracefully();
        this.channel.close();
    }

    @ChannelHandler.Sharable
    class NettyServerHandler extends SimpleChannelInboundHandler<DefaultMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DefaultMessage msg) throws Exception {
            processRequest(ctx, msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
           //NettyConnectionHolder.remove(((NioSocketChannel) ctx.channel()));
            super.channelUnregistered(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (IdleState.READER_IDLE.equals((event.state()))) {
                    String remoteAddr = RemotingUtils.parseChannelRemoteAddr(ctx.channel());
                    logger.warn("NETTY SERVER PIPELINE: IDLE RemoteAddress [{}]", remoteAddr);
                }
            }
            super.userEventTriggered(ctx, evt);
        }
    }

}
