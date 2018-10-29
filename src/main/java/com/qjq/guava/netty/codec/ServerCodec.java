package com.qjq.guava.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerCodec {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup pGroup = new NioEventLoopGroup();//用于处理服务器接收客户的连接,ParentGroup
        EventLoopGroup cGroup = new NioEventLoopGroup();//进行网络读写的,进行业务操作
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(pGroup, cGroup)//绑定两个线程组
                .handler(new LoggingHandler(LogLevel.INFO)) //打印输出和初始化日志，这里是INFO级别的日志
                .channel(NioServerSocketChannel.class)//指定NIO的模式,服务器端模式（Socket通信），也可以是Client或者UDP
                .option(ChannelOption.SO_BACKLOG, 1024) //TCP缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024) //发送区缓存
                .option(ChannelOption.SO_RCVBUF, 32 * 1024) //接收区缓存
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerCodecInit());
        ChannelFuture cf = bootstrap.bind(8765).sync();//绑定接口，同步的

        cf.channel().closeFuture().sync(); //等待关闭,主线程阻塞到这里
        System.out.println("server connect1 close");

        pGroup.shutdownGracefully(); //关闭parent线程组，资源释放
        cGroup.shutdownGracefully(); //关闭child线程组，资源释放
    }

    /**
     * 静态内部类，用于初始化channel的一些方法
     */
    private static class ServerCodecInit extends ChannelInitializer {
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
            ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
            //对管道内数据进行处理通过HandlerAdapter类
            ch.pipeline().addLast(new ServerCodecHandler());
        }
    }
}
