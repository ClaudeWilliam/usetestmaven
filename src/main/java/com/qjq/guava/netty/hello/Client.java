package com.qjq.guava.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//发送数据的线程组
        Bootstrap b = new Bootstrap();
        b.group(group) //启动类中增加线程组
                .channel(NioSocketChannel.class) //指定管道类型
                .handler(
                new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws  Exception{
                       //对管道内数据进行处理通过HandlerAdapter类
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                }
        );
        ChannelFuture cf=b.connect("127.0.0.1",8765).sync(); //连接指定的服务器地址，这里是之前我们的设置的

        ChannelFuture cf1=b.connect("127.0.0.1",8764).sync(); //客户端连接第二个连接地址

        cf.channel().write(Unpooled.copiedBuffer("hello netty".getBytes())); //写入数据
        cf.channel().write(Unpooled.copiedBuffer("hello netty1".getBytes())); //写入数据
        cf.channel().write(Unpooled.copiedBuffer("hello netty2".getBytes())); //写入数据
        cf.channel().write(Unpooled.copiedBuffer("hello netty3".getBytes())); //写入数据
        cf.channel().flush();//发送数据

//        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("hello world test".getBytes())); //连接2发送数据

        TimeUnit.SECONDS.sleep(10);
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("10S Send message".getBytes())); //写入发送数据
        cf.addListener(ChannelFutureListener.CLOSE);//客户端去断开连接，不过这样断开连接是接收不到服务器端发货来的数据


        cf.channel().closeFuture().sync();//主线程阻塞到这里，直至关闭，第一个链接
        System.out.println("client connect1 close");
//        cf1.channel().closeFuture().sync();//主线程阻塞到这里，直至关闭，第一个链接
//        System.out.println("client connect2 close");
        group.shutdownGracefully(); //关闭线程组，释放资源
    }
}
