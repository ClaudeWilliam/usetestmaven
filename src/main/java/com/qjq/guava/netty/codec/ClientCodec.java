package com.qjq.guava.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;

public class ClientCodec {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//发送数据的线程组
        Bootstrap b = new Bootstrap();
        b.group(group) //启动类中增加线程组
                .channel(NioSocketChannel.class) //指定管道类型
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ClientCodecInit());
        ChannelFuture cf = b.connect("127.0.0.1", 8765).sync(); //连接指定的服务器地址，这里是之前我们的设置的

        cf.channel().closeFuture().sync();//主线程阻塞到这里，直至关闭，第一个链接
        System.out.println("client connect close");
        group.shutdownGracefully(); //关闭线程组，释放资源
    }

    /**
     * 静态内部类用于初始化客户端的Channel
     */
    private static class ClientCodecInit extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
            //增加marshalling的解码对象
            ch.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
            //对管道内数据进行处理通过HandlerAdapter类
            ch.pipeline().addLast(new ClientCodecHandler());
        }
    }
}
