package com.m.monitor.me.service.transfer.server;

import com.m.monitro.me.common.utils.TransferSnappyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class AbstractExpressWayServer extends ChannelInitializer<SocketChannel> {

    protected EventLoopGroup bossGroup = new NioEventLoopGroup();
    protected EventLoopGroup wokerGroup = new NioEventLoopGroup();
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline pipeline = ch.pipeline();
         pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
         pipeline.addLast(new LengthFieldPrepender(4));
         pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
         pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
         pipeline.addLast(new ExpressWayHandler());
    }

    public void bind(int port) throws Exception{
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(this);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            close();
        }
    }

    public void close(){
        if (bossGroup!=null) {
            bossGroup.shutdownGracefully();
        }
        if (wokerGroup!=null) {
            wokerGroup.shutdownGracefully();
        }
    }
    public  abstract boolean receive(ChannelHandlerContext ctx,String msg);

    class ExpressWayHandler extends SimpleChannelInboundHandler<String>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx,String msg) throws Exception {
            try {
                String msgStr= TransferSnappyUtil.uncompressToStr(msg);
                receive(ctx,msgStr);
            } catch (Exception e) {
                log.error("receive-uncompress exception:",e);
            }
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
