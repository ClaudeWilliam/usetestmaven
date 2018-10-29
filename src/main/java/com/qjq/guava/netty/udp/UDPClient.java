package com.qjq.guava.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//发送数据的线程组，设置里面的线程个数2个
        Bootstrap b = new Bootstrap();
        b.group(group) //启动类中增加线程组
                .channel(NioDatagramChannel.class) //指定管道类型
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ClientUDPInit());
        ChannelFuture cf = b.bind(6789).sync(); //连接指定的服务器地址，这里是之前我们的设置的
        cf.channel().closeFuture().sync();//主线程阻塞到这里，直至关闭，第一个链接
        System.out.println("client connect close");
        group.shutdownGracefully(); //关闭线程组，释放资源
    }

    /**
     * 静态内部类用于初始化客户端的Channel
     */
    private static class ClientUDPInit extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            //增加对数据的编解码工作，UDP和处理之后的操作
            ch.pipeline().addLast(new ClientUDPHandler());
        }
    }
}