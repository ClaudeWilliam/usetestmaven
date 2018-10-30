package com.qjq.guava.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class UDPServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();//TCP Socket使用的是两个线程组，但是UDP只有一个
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)//绑定线程组
                .handler(new LoggingHandler(LogLevel.INFO)) //打印输出和初始化日志，这里是INFO级别的日志
                .channel(NioDatagramChannel.class)//UDP的类型Channel
                .option(ChannelOption.SO_BROADCAST, true) //设置Channel的属性，这里使用的是广播的机制
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024)// 设置UDP写缓冲区为1M
                .handler(new ServerUDPInit());
        ChannelFuture cf = bootstrap.bind(1234).sync();//绑定接口

        cf.channel().closeFuture().sync();
        System.out.println("server connect1 close");
        group.shutdownGracefully(); //关闭parent线程组，资源释放
    }

    /**
     * 静态内部类用于初始化客户端的Channel
     */
    private static class ServerUDPInit extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            //增加对数据的编解码工作，UDP和处理之后的操作
            ch.pipeline().addLast(new ServerUDPHandler());
            //设置读取超时时间。超时后断开，5S
            ch.pipeline().addLast(new ReadTimeoutHandler(5,TimeUnit.SECONDS));
        }
    }
}
