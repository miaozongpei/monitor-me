package com.m.monitor.me.service.transfer.server;

import com.m.monitro.me.common.utils.TransferSnappyUtil;
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

import java.net.InetSocketAddress;

/**
 * 传输通道服务端抽象类（socket）
 *
 * @Author: miaozp
 * @Date: 2020/10/31 4:19 下午
 **/
@Slf4j
public abstract class AbstractExpressWayServer extends ChannelInitializer<SocketChannel> {

    protected EventLoopGroup bossGroup = new NioEventLoopGroup();
    protected EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    /**
     *定义抽象接收消息后处理方法
     * @author: miaozp
     * @date: 2020/10/31 4:45 下午
     * @param ctx:
     * @param msg:
     * @return: boolean
     **/
    public abstract boolean receive(ChannelHandlerContext ctx, String msg);

    /**
     * 初始化socket通道
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:21 下午
     * @Param: [ch]
     * @Return: void
     **/
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ExpressWayHandler());
    }

    /**
     * 绑定端口
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:21 下午
     * @Param: [port]
     * @Return: void
     **/
    public void bind(int port) throws Exception {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(this);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            close();
        }
    }

    /**
     * 关闭通道
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:22 下午
     * @Param: []
     * @Return: void
     **/
    public void close() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 定义简单对象传输处理类
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:23 下午
     **/
    class ExpressWayHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            try {
                //对传输数据进行解压，以此减少传输信息体量，减小网络开销
                String msgStr = TransferSnappyUtil.uncompressToStr(msg);
                receive(ctx, msgStr);
            } catch (Exception e) {
                log.error("[MonitorMe admin] uncompress clientIp:{} msg:{} exception:", ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress(), msg, e);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
