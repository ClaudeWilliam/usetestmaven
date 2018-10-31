package com.qjq.guava.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {


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
                .childHandler(new WebSocketInit());
        ChannelFuture cf = bootstrap.bind(8765).sync();//绑定接口，同步的

        cf.channel().closeFuture().sync(); //等待关闭,主线程阻塞到这里
        System.out.println("server connect close");

        pGroup.shutdownGracefully(); //关闭parent线程组，资源释放
        cGroup.shutdownGracefully(); //关闭child线程组，资源释放
    }
    
    /**
     *@ Function: 用于初始化
     *@ Author:junqiang.qiu @ Date: 2018/10/31 10:46
     */
    private static class WebSocketInit extends ChannelInitializer {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpServerCodec()); //田间关于Http请求的转码器，将ByteBuf转成httprequest
            pipeline.addLast(new HttpObjectAggregator(64*1024));//设置Http请求的大小64K
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpRequestHandler("/ws"));
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws")); //关于websoket的解码处理器
            pipeline.addLast(new ServerSocketHandler());//socket处理handler
    }
    }
}
