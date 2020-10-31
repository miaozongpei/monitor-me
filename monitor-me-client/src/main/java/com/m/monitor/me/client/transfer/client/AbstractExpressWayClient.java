package com.m.monitor.me.client.transfer.client;

import com.m.monitro.me.common.utils.TransferSnappyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象传输通道客户端
 *
 * @Author: miaozp
 * @Date: 2020/10/31 6:25 下午
 **/
@Slf4j
public abstract class AbstractExpressWayClient extends ChannelInitializer<SocketChannel> {
    /**
     * 事件池
     */
    protected EventLoopGroup eventLoopGroup;
    /**
     * 客户端通道
     */
    protected Channel clientChannel;
     /**
      * 初始化通道
      * @Author: miaozp
      * @Date: 2020/10/31 6:26 下午
      * @param ch:
      * @return: void
      **/
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        ExpressWayHandler channelHandler = new ExpressWayHandler();
        pipeline.addLast(channelHandler);
    }
    /**
     * 异步连接监控服务端
     * @Author: miaozp
     * @Date: 2020/10/31 6:27 下午
     * @param host:
     * @param port:
     * @return: void
     **/
    protected void asyConnect(String host, int port) {
        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connect(host, port);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[MonitorMe client] asyConnect exception:", e);
                    close();
                }
            }
        });
        //设置成守护线程
        connectThread.setDaemon(true);
        connectThread.start();
    }
    /**
     * 建立连接
     * @Author: miaozp
     * @Date: 2020/10/31 6:28 下午
     * @param host:
     * @param port:
     * @return: void
     **/
    private void connect(String host, int port) throws Exception {
        try {
            synchronized (this) {
                if (!isConnected()) {
                    log.info("[MonitorMe client] ExpressWayClient is connecting  host:{}-port:{}", host, port);
                    eventLoopGroup = new NioEventLoopGroup();
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                            .handler(this);
                    ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
                    clientChannel = channelFuture.channel();
                    clientChannel.closeFuture().sync();
                }
            }
        } finally {
            close();
        }
    }

    public boolean isConnected() {
        return clientChannel != null;
    }
    /**
     * 检查是否连接上服务端
     * @Author: miaozp
     * @Date: 2020/10/31 6:28 下午
     * @param host:
     * @param port:
     * @return: boolean
     **/
    public boolean checkAndConnect(String host, int port) {
        if (clientChannel != null) {
            if (clientChannel.isActive()) {
                return true;
            } else {
                close();
                return false;
            }
        } else {
            asyConnect(host, port);
            return false;
        }
    }
    /**
     * 关闭客户端
     * @Author: miaozp
     * @Date: 2020/10/31 6:28 下午
     * @return: void
     **/
    public void close() {
        //关闭客户端套接字
        if (clientChannel != null) {
            clientChannel.close();
            clientChannel = null;
        }
        //关闭客户端线程组
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }
    /**
     * 服务点返回消息
     * @Author: miaozp
     * @Date: 2020/10/31 6:29 下午
     * @param ctx:
     * @param msg:
     * @return: void
     **/
    public abstract void replay(ChannelHandlerContext ctx, String msg);

    /**
     * 客户端发送消息
     * @Author: miaozp
     * @Date: 2020/10/31 6:29 下午
     * @param msg:
     * @return: boolean
     **/
    public boolean send(String msg) {
        if (clientChannel == null) {
            return false;
        }
        try {
            String msgStr = TransferSnappyUtil.compressToStr(msg);
            clientChannel.writeAndFlush(msgStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 等待连接
     * @Author: miaozp
     * @Date: 2020/10/31 6:30 下午
     * @return: void
     **/
    protected void waitingConnect() {
        while (clientChannel == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 定义简单对象传输处理类
     * @Author: miaozp
     * @Date: 2020/10/31 6:30 下午
     **/
    class ExpressWayHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            replay(ctx, msg);
        }

        /**
         * 当服务器端与客户端进行建立连接的时候会触发，如果没有触发读写操作，则客户端和客户端之间不会进行数据通信，也就是channelRead0不会执行，
         * 当通道连接的时候，触发channelActive方法向服务端发送数据触发服务器端的handler的channelRead0回调，然后
         * 服务端向客户端发送数据触发客户端的channelRead0，依次触发。
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
