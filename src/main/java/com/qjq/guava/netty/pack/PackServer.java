package com.qjq.guava.netty.pack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class PackServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup pGroup=new NioEventLoopGroup();//用于处理服务器接收客户的连接,ParentGroup
        EventLoopGroup cGroup=new NioEventLoopGroup();//进行网络读写的,进行业务操作
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(pGroup,cGroup)//绑定两个线程组
                .channel(NioServerSocketChannel.class)//指定NIO的模式,服务器端模式（Socket通信），也可以是Client或者UDP
                .option(ChannelOption.SO_BACKLOG,1024) //TCP缓冲区
                .option(ChannelOption.SO_SNDBUF,32*1024) //发送区缓存
                .option(ChannelOption.SO_RCVBUF,32*1024) //接收区缓存
                .option(ChannelOption.SO_KEEPALIVE,true)
                .childHandler( new ChannelInitializer<SocketChannel>() {
                                   protected void initChannel(SocketChannel socketChannel) throws  Exception{
                                      //指定特殊分割符的方式来分割tcp的流,转成buf
                                       ByteBuf buf=Unpooled.copiedBuffer("_$".getBytes());
                                       //进行分割,1024的缓存大小
                                       socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                                       //但是定长的分隔,但是定长有一个问题,就是定长的时候,当你的长度不足5的时候,他就会把消息丢掉,会导致乱码
                                       //如果想让消息不丢失的话要用空格去填补.
//                                       socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(5));
                                       //设置字符串的解码器
                                       socketChannel.pipeline().addLast(new StringDecoder());
                                       //对管道内数据进行处理通过HandlerAdapter类
                                       socketChannel.pipeline().addLast(new ServerPackHandler());
                                   }
                               }
                );
        ChannelFuture cf= bootstrap.bind(8765).sync();//绑定接口，同步的

        cf.channel().closeFuture().sync(); //等待关闭,主线程阻塞到这里
        System.out.println("server connect1 close");

        pGroup.shutdownGracefully(); //关闭parent线程组，资源释放
        cGroup.shutdownGracefully(); //关闭child线程组，资源释放
    }
}
