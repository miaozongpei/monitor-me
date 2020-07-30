package com.m.monitor.me.client.transfer.client;

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


public abstract class AbstractExpressWayClient extends ChannelInitializer<SocketChannel> {
    /**
     * 时间池
     */
    protected EventLoopGroup eventLoopGroup;
     /**
     * 客户端通道
     */
     protected Channel clientChannel;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        ExpressWayHandler channelHandler=new ExpressWayHandler();
        pipeline.addLast(channelHandler);
    }
    public AbstractExpressWayClient(){

    }
    public AbstractExpressWayClient(String host, int port) {
        asyConnect(host,port);//异步建立链接
        waitingConnect();//等待建立链接
    }

    protected void asyConnect(String host, int port) {
        Thread connectThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connect(host,port);
                } catch (Exception e) {
                    e.printStackTrace();
                    close();
                }
            }
        });
        connectThread.setDaemon(true);//设置成守护线程
        connectThread.start();
    }

    private void connect(String host,int port) throws Exception{
        try{
            synchronized (this) {
                if (!isConnected()) {
                    eventLoopGroup= new NioEventLoopGroup();
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                            .handler(this);
                    ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
                    clientChannel = channelFuture.channel();
                    clientChannel.closeFuture().sync();
                }
            }
        }finally {
            close();
        }
    }
    public boolean isConnected(){
        return clientChannel!=null;
    }
    public boolean checkAndConnect(String host,int port){
        if(clientChannel!=null){
            if (clientChannel.isActive()){
                return true;
            }else{
                close();
                return false;
            }
        }else{
            asyConnect(host,port);
            return false;
        }
    }
    public void close(){
        //关闭客户端套接字
        if(clientChannel!=null){
            clientChannel.close();
            clientChannel=null;
        }
        //关闭客户端线程组
        if (eventLoopGroup!=null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public abstract void replay(ChannelHandlerContext ctx,String msg);

    public  boolean send(String msg){
        if (clientChannel==null){
            return false;
        }
        try {
            clientChannel.writeAndFlush(msg);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    protected void waitingConnect(){
        while (clientChannel==null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class ExpressWayHandler extends SimpleChannelInboundHandler<String>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            //System.out.println(ctx.channel().remoteAddress());
            //System.out.println("client output: "+msg);
            replay(ctx,msg);
        }

        /**
         * 当服务器端与客户端进行建立连接的时候会触发，如果没有触发读写操作，则客户端和客户端之间不会进行数据通信，也就是channelRead0不会执行，
         * 当通道连接的时候，触发channelActive方法向服务端发送数据触发服务器端的handler的channelRead0回调，然后
         * 服务端向客户端发送数据触发客户端的channelRead0，依次触发。
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //ctx.writeAndFlush("来自与客户端的问题!");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
